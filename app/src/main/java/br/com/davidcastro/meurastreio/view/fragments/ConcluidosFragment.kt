package br.com.davidcastro.meurastreio.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.davidcastro.meurastreio.databinding.FragmentListTemplateBinding

class ConcluidosFragment : Fragment() {

    private lateinit var binding: FragmentListTemplateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListTemplateBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }
}