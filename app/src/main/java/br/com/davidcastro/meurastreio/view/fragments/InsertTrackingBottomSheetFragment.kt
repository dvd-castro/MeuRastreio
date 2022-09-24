package br.com.davidcastro.meurastreio.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.davidcastro.meurastreio.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsertTrackingBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_insert_tracking_bottom_sheet, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}