package br.com.davidcastro.inserttracking.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import br.com.davidcastro.inserttracking.databinding.FragmentInsertTrackingBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsertTrackingBottomSheetFragment(private val listener: (codigo: String, nome: String) -> Unit) : BottomSheetDialogFragment() {

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

        binding.textInputCode.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText?.length == 13) {
                binding.textInputName.requestFocus()
            }
        }
    }

    private fun getEditTextFields() {
        listener.invoke(
            getInputCodeText(),
            getInputNameText()
        )
    }

    private fun getInputCodeText(): String = binding.textInputCode.editText?.text.toString()

    private fun getInputNameText(): String = binding.textInputName.editText?.text.toString()

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}