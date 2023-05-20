package br.com.davidcastro.trackingdetails.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.trackingdetails.BuildConfig
import br.com.davidcastro.trackingdetails.R
import br.com.davidcastro.trackingdetails.databinding.FragmentTrackingDetailsBottomSheetBinding
import br.com.davidcastro.trackingdetails.di.TrackingDetailsModule
import br.com.davidcastro.trackingdetails.listeners.OnCloseBottomSheetDialogFragment
import br.com.davidcastro.trackingdetails.view.adapters.TrackingDetailsAdapter
import br.com.davidcastro.trackingdetails.viewmodel.TrackingDetailsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackingDetailsBottomSheetFragment(private val tracking: TrackingModel, private val onCloseListener: OnCloseBottomSheetDialogFragment) : BottomSheetDialogFragment() {

    private val viewModel by viewModel<TrackingDetailsViewModel>()

    private lateinit var binding: FragmentTrackingDetailsBottomSheetBinding
    lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        TrackingDetailsModule.inject()
        binding = FragmentTrackingDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAD()
        setName()
        setCode()
        setList()
        configDialog()
        configClickListeners()
        setObservable()
    }

    private fun setObservable() {
        viewModel.hasDeleted.observe(viewLifecycleOwner, ::onDelete)
    }

    override fun onDestroy() {
        super.onDestroy()
        onCloseListener.launch()
    }

    private fun onDelete(hasDeleted: Boolean) {
        if(hasDeleted) {
            dismiss()
        }
    }

    private fun initAD() {
        if(!BuildConfig.DEBUG) {
            val adRequest = AdRequest.Builder().build()
            binding.adView.visibility = View.VISIBLE
            binding.adView.loadAd(adRequest)
        }
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

        binding.llShare.setOnClickListener {
            shareLastEvent()
        }
    }

    private fun configDialog() {
        alertDialog = this.let {

            val builder = AlertDialog.Builder(requireContext())

            builder.apply {
                setTitle(getString(R.string.title_atencao))
                setMessage(getString(R.string.message_deseja_excluir_o_item))

                setPositiveButton(getString(R.string.action_to_confirm)) { _, _ ->
                    viewModel.delete(tracking.code)
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, _ ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
    }

    private fun shareLastEvent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, tracking.getStatusToShare())
            type = "text/plain"
        }

        startActivity(Intent.createChooser(sendIntent, null))
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}