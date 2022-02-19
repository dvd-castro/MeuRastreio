package br.com.davidcastro.meurastreio.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.FragmentListTemplateBinding
import br.com.davidcastro.meurastreio.view.adapters.RastreioAdapter
import br.com.davidcastro.meurastreio.view.listeners.ClickListener
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConcluidosFragment : Fragment(), ClickListener {

    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentListTemplateBinding
    private lateinit var rastreioAdapter: RastreioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListTemplateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    private fun initObservers() {
        viewModel.databaseList.observe(viewLifecycleOwner, ::whenGetDatabaseList)
    }

    private fun initUi() {
        configRecyclerView()
        configSwipeRefresh()
    }

    private fun configRecyclerView() {
        rastreioAdapter = RastreioAdapter(this)

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = rastreioAdapter
            this.setHasFixedSize(false)
        }
    }

    private fun configSwipeRefresh() {
        binding.swipe.setOnRefreshListener {
            viewModel.reload().invokeOnCompletion {
                binding.swipe.isRefreshing = false
            }
        }
    }

    private fun whenGetDatabaseList (listOfTracking: MutableList<RastreioModel>) {
        val filteredList = listOfTracking.filter { rastreio ->
            rastreio.eventos.first().status == getString(R.string.status_entregue)
        }

        showRecycler(filteredList)
    }

    private fun showRecycler(trackingList: List<RastreioModel>){
        if(trackingList.isNotEmpty()) {
            rastreioAdapter.submitList(trackingList)
            binding.recyclerView.visibility = View.VISIBLE
            binding.warningText.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.warningText.visibility = View.VISIBLE
            binding.warningText.text = getString(R.string.message_nao_ha_encomendas_concluidas)
        }
    }

    override fun onItemClick(codigo: String) {
        val bottomSheetFragment = DetalhesFragment.newInstance(codigo)
        bottomSheetFragment.show(parentFragmentManager, "TAG")
    }

}