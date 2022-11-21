package br.com.davidcastro.trackingdetails.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.trackingdetails.databinding.FragmentTrackingDetailsBottomSheetBinding
import br.com.davidcastro.trackingdetails.view.adapters.TrackingDetailsAdapter
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
        setName()
        setCode()
        setList()
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

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}