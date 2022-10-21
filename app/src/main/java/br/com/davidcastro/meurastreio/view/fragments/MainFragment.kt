package br.com.davidcastro.meurastreio.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.inserttracking.view.fragments.InsertTrackingBottomSheetFragment
import br.com.davidcastro.inserttracking.view.listeners.InsertFragmentListener
import br.com.davidcastro.meurastreio.view.adapters.TrackingAdapter
import br.com.davidcastro.meurastreio.view.listeners.ClickListener
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import br.com.davidcastro.ui.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), ClickListener, InsertFragmentListener {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentMainBinding
    private val trackingAdapter = TrackingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservers()
        getAllTrackingInDataBase()
    }

    private fun initObservers() {
        viewModel.tracking.observe(viewLifecycleOwner, ::whenHasTracking)
    }

    private fun initUI() {
        setInsertClickListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvItensEmAndamento.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = trackingAdapter
        }
    }
    private fun setInsertClickListener() {
        binding.btnAdd.setOnClickListener {
            openInsertTrackingFragment()
        }
    }

    private fun openInsertTrackingFragment() {
        val modalBottomSheet = InsertTrackingBottomSheetFragment(this@MainFragment)
        modalBottomSheet.showNow(parentFragmentManager, InsertTrackingBottomSheetFragment.TAG)
    }

    private fun getTracking(codigo: String) {
        viewModel.getTracking(codigo)
    }

    private fun getAllTrackingInDataBase() {
        viewModel.getAllTrackingInDataBase()
    }

    private fun whenHasTracking(listTrackingHome: List<TrackingModel>) {
        binding.rvItensEmAndamento.visibility = View.VISIBLE
        binding.includeLayoutEmptyState.layoutEmptyState.visibility = View.GONE
        trackingAdapter.submitList(listTrackingHome)
    }

    override fun onItemClick(codigo: String) {
        TODO("Not yet implemented")
    }

    override fun sendTrackingCode(code: String, name: String?) {
        getTracking(code)
    }
}