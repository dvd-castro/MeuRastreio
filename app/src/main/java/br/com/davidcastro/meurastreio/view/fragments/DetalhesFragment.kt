package br.com.davidcastro.meurastreio.view.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.FragmentDetalhesBinding
import br.com.davidcastro.meurastreio.helpers.utils.NetworkUtils
import br.com.davidcastro.meurastreio.helpers.utils.showSnackbar
import br.com.davidcastro.meurastreio.view.adapters.DetalhesAdapter
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

private const val CODIGO_RASTREIO = "codigo"

class DetalhesFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentDetalhesBinding
    private lateinit var alertDialog : AlertDialog
    private lateinit var mMap: GoogleMap
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
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = DetalhesAdapter(data)
            this.setHasFixedSize(false)
        }
    }

    private fun configDialog() {
        alertDialog = this.let {

            val builder = AlertDialog.Builder(requireContext())

            builder.apply {
                setTitle(resources.getString(R.string.title_atencao))
                setMessage(resources.getString(R.string.message_deseja_excluir_o_item))

                setPositiveButton(resources.getString(R.string.action_to_confirm)) { dialog, id ->
                    deleteTracking()
                }

                setNegativeButton(resources.getString(R.string.action_to_cancel)) { dialog, id ->
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
            mMap.addMarker(MarkerOptions().position(location).title(resources.getString(R.string.title_map_pin)))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        }
    }

    private fun setAddressOnMap(strAddress: String) {

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var geoResults: List<Address>

        try {
            geoResults = geocoder.getFromLocationName(strAddress, 1)

            while (geoResults.isEmpty()) {
                geoResults = geocoder.getFromLocationName(strAddress, 1)
            }

            if (geoResults.isNotEmpty()) {
                val addr = geoResults[0]
                latLng = LatLng(addr.latitude, addr.longitude)
                initMap()
            } else {
                showSnackbar(binding.root, getString(R.string.error_endereco))
            }

            onLoader(false)

        } catch (ex: Exception) {
            onLoader(false)
            ex.localizedMessage?.let { localizedMessage ->
                Log.e("ERROR -> OnShowMap", localizedMessage)
            }
        }

    }

    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapsContainer) as SupportMapFragment

        mapFragment.getMapAsync(this)
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
        codigo?.let { viewModel.deleteTrackingOnDB(it) }
    }

    private fun whenDeleteIsComplete(isDeleted: Boolean){
        if(isDeleted){
            dismiss()
        }
    }

    private fun whenGetResult(tracking: RastreioModel){
        binding.model = tracking

        configRecyclerView(tracking.eventos)

        if(NetworkUtils.hasConnectionActive(requireContext())) {
            Handler(Looper.getMainLooper()).postDelayed(
                { setAddressOnMap(tracking.eventos.first().local) },
                1000)
        } else {
            binding.wifiError.visibility = View.VISIBLE
            onLoader(false)
        }
    }

}