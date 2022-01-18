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
import br.com.davidcastro.meurastreio.view.adapters.DetalhesAdapter
import br.com.davidcastro.meurastreio.viewModel.DetalhesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val CODIGO_RASTREIO = "codigo"

class DetalhesFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetalhesBinding
    private val viewModel: DetalhesViewModel by viewModel()

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

        getTracking()
        initUi()
        initObservers()
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

    private fun initObservers(){
        viewModel.getResult.observe(this, ::whenGetResult)
    }

    private fun initUi(){
        configDialog()
    }

    private fun configRecyclerView(data: List<EventosModel>){
        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = DetalhesAdapter(data)
            this.setHasFixedSize(false)
        }
    }

    private fun configDialog(){
        alertDialog = this.let {

            val builder = AlertDialog.Builder(requireContext())

            builder.apply {
                setTitle("Atenção!")
                setMessage("Excluir o item?")
                setPositiveButton("Confirmar") { dialog, id ->
                    deleteTracking()
                }
                setNegativeButton("Cancelar") { dialog, id ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        latLng?.let{
            val location = LatLng(it.latitude, it.longitude)
            mMap.addMarker(MarkerOptions().position(location).title("Última localização da encomenda"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        }
    }

    private fun determineLatLngFromAddress(strAddress: String): LatLng? {

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var geoResults: List<Address>? = null
        try {
            geoResults = geocoder.getFromLocationName(strAddress, 1)
            while (geoResults!!.isEmpty()) {
                geoResults = geocoder.getFromLocationName(strAddress, 1)
            }
            if (geoResults.isNotEmpty()) {
                val addr = geoResults[0]
                latLng = LatLng(addr.latitude, addr.longitude)
                initMap()
            }
        } catch (e: Exception) {
            print(e.message)
        }
        return latLng
    }

    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapsContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getTracking(){
        codigo?.let { viewModel.getTracking(it) }
    }

    private fun deleteTracking(){
        codigo?.let { viewModel.deleteTracking(it) }
    }

    private fun whenGetResult(tracking: RastreioModel){
        binding.model = tracking
        configRecyclerView(tracking.eventos)
        determineLatLngFromAddress(tracking.eventos.first().local)
    }

}