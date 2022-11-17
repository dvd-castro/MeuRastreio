package br.com.davidcastro.inserttracking.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.davidcastro.inserttracking.databinding.FragmentInsertTrackingBottomSheetBinding
import br.com.davidcastro.inserttracking.view.listeners.InsertFragmentListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsertTrackingBottomSheetFragment(private val listener: InsertFragmentListener) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInsertTrackingBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertTrackingBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setActionClicks()
    }

    private fun setActionClicks() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnAdd.setOnClickListener {
            getEditTextFields()
            dismiss()
        }
    }

    private fun getEditTextFields() {
        listener.sendTrackingCode(
            binding.textInputCode.editText?.text.toString(),
            binding.textInputName.editText?.text.toString()
        )
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}