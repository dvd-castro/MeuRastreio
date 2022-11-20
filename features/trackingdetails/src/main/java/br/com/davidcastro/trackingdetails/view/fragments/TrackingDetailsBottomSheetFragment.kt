package br.com.davidcastro.trackingdetails.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.trackingdetails.databinding.FragmentTrackingDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TrackingDetailsBottomSheetFragment(private val tracking: TrackingModel) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTrackingDetailsBottomSheetBinding

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

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}