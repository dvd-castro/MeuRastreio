package br.com.davidcastro.trackingdetails.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.trackingdetails.R
import br.com.davidcastro.trackingdetails.databinding.FragmentTrackingDetailsBottomSheetBinding
import br.com.davidcastro.trackingdetails.view.adapters.TrackingDetailsAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TrackingDetailsBottomSheetFragment(private val tracking: TrackingModel) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTrackingDetailsBottomSheetBinding
    lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackingDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAD()
        setName()
        setCode()
        setList()
        configDialog()
        configClickListeners()
    }

    private fun initAD() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setList() {
        binding.rvEventList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = TrackingDetailsAdapter(tracking.events)
        }
    }

    private fun setName() {
        if(!tracking.name.isNullOrBlank()) {
            binding.tvName.text = tracking.name
            binding.tvName.visibility = View.VISIBLE
        }
    }

    private fun setCode() {
        binding.tvCode.text = tracking.code
    }

    private fun configClickListeners() {
        binding.llDelete.setOnClickListener {
            alertDialog.show()
        }
    }
    private fun configDialog() {
        alertDialog = this.let {

            val builder = AlertDialog.Builder(requireContext())

            builder.apply {
                setTitle(getString(R.string.title_atencao))
                setMessage(getString(R.string.message_deseja_excluir_o_item))

                setPositiveButton(getString(R.string.action_to_confirm)) { dialog, id ->
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, id ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}