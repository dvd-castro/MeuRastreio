package br.com.davidcastro.meurastreio.view.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.FragmentDetalhesBinding
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import br.com.davidcastro.meurastreio.view.adapters.DetalhesAdapter
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

private const val CODIGO_RASTREIO = "codigo"

class DetalhesFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private val viewModel: MainViewModel by sharedViewModel()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var binding: FragmentDetalhesBinding
    private lateinit var alertDialog: AlertDialog
    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    private var codigo: String? = null
    private var latLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            codigo = it.getString(CODIGO_RASTREIO)
        }

        binding = FragmentDetalhesBinding.inflate(layoutInflater, container, false)
        initAD()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        getTracking()
    }

    companion object {
        @JvmStatic
        fun newInstance(codigo: String) =
            DetalhesFragment().apply {
                arguments = Bundle().apply {
                    putString(CODIGO_RASTREIO, codigo)
                }
            }
    }

    private fun initAD(){
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun initObservers() {
        viewModel.getOfflineResult.observe(this, ::whenGetResult)
        viewModel.deleteOnComplete.observe(this, ::whenDeleteIsComplete)
    }

    private fun initUi() {
        configDialog()
        setListeners()
    }

    private fun setListeners() {
        binding.tvApagar.setOnClickListener {
            alertDialog.show()
            setDialogTextActionColor()
        }
    }

    private fun configRecyclerView(data: List<EventosModel>) {
        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = DetalhesAdapter(data)
            this.setHasFixedSize(false)
        }
    }

    private fun configDialog() {
        alertDialog = this.let {

            val builder = AlertDialog.Builder(requireContext())

            builder.apply {
                setTitle(getString(R.string.title_atencao))
                setMessage(getString(R.string.message_deseja_excluir_o_item))

                setPositiveButton(getString(R.string.action_to_confirm)) { dialog, id ->
                    deleteTracking()
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, id ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
    }

    private fun setDialogTextActionColor() {
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.red))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        latLng?.let{
            val location = LatLng(it.latitude, it.longitude)
            mMap?.addMarker(MarkerOptions().position(location).title(getString(R.string.title_map_pin)))
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
        }
    }

    private fun setAddressOnMap(strAddress: String) = if(strAddress != "Pa??s//") {
        try {
            scope.launch {
                val geocoder = Geocoder(context, Locale.getDefault())

                val geoResults: List<Address> = geocoder.getFromLocationName(strAddress, 1)

                if (geoResults.isNotEmpty()) {
                    latLng = LatLng(geoResults.first().latitude, geoResults.first().longitude)
                } else {
                    showAlertView(getString(R.string.error_endereco))
                }

                withContext(mainScope.coroutineContext){
                    initMap()
                    onLoader(false)
                }
            }
        } catch (ex: Exception) {
            onLoader(false)
            showAlertView(getString(R.string.error_endereco))

            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCrashlyticsCollectionEnabled(true)

            codigo?.let {
                crashlytics.setUserId(it)
                crashlytics.setCustomKeys {
                    key("itemCode","$codigo")
                    key("itemAddres", strAddress)
                    key("latitude", "${latLng?.latitude}")
                    key("longitude", "${latLng?.longitude}")
                }
            }
            crashlytics.recordException(ex)
        }

    } else {
        onLoader(false)
        showAlertView(getString(R.string.error_rastreio_internacional))
    }

    private fun initMap() {
        mapFragment = childFragmentManager
            .findFragmentById(R.id.mapsContainer) as SupportMapFragment

        mapFragment?.getMapAsync(this)
    }

    private fun onLoader(boolean: Boolean){
        if (boolean)
            binding.loader.visibility = View.VISIBLE
        else
            binding.loader.visibility = View.GONE
    }

    private fun getTracking(){
        codigo?.let { viewModel.getTrackingOnDb(it) }
    }

    private fun deleteTracking(){
        codigo?.let { viewModel.deleteTrackingOnDb(it) }
    }

    private fun whenDeleteIsComplete(isDeleted: Boolean){
        if(isDeleted){
            dismiss()
        }
    }

    private fun whenGetResult(tracking: RastreioModel){
        binding.model = tracking

        configRecyclerView(tracking.eventos)

        context?.let {
            if(NetworkUtils.hasConnectionActive(it)) {
                setAddressOnMap(tracking.eventos.first().local)
            } else {
                binding.wifiError.visibility = View.VISIBLE
                onLoader(false)
            }
        } ?: showAlertView(getString(R.string.error_endereco))
    }

    private fun showAlertView(message: String){
        binding.warning.visibility = View.VISIBLE
        binding.warningText.text = message
    }

}