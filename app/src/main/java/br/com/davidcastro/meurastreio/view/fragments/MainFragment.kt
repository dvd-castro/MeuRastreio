package br.com.davidcastro.meurastreio.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.inserttracking.view.fragments.InsertTrackingBottomSheetFragment
import br.com.davidcastro.inserttracking.view.listeners.InsertFragmentListener
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.databinding.FragmentMainBinding
import br.com.davidcastro.meurastreio.view.adapters.TrackingAdapter
import br.com.davidcastro.meurastreio.view.listeners.ClickListener
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import br.com.davidcastro.trackingdetails.listeners.OnCloseBottomSheetDialogFragment
import br.com.davidcastro.trackingdetails.view.fragments.TrackingDetailsBottomSheetFragment
import br.com.davidcastro.ui.utils.UiUtils.showErrorSnackbar
import br.com.davidcastro.ui.utils.UiUtils.showSnackbar
import com.google.android.gms.ads.AdRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment(), ClickListener, InsertFragmentListener, OnCloseBottomSheetDialogFragment {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentMainBinding
    private val trackingAdapterCompleted = TrackingAdapter(this)
    private val trackingAdapterInProgress = TrackingAdapter(this)

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
    }

    private fun initAD() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView2.loadAd(adRequest)
    }

    private fun initObservers() {
        viewModel.trackingInProgress.observe(viewLifecycleOwner, ::whenHasTrackingInProgress)
        viewModel.trackingCompleted.observe(viewLifecycleOwner, ::whenHasTrackingCompleted)
        viewModel.trackingAlreadyExists.observe(viewLifecycleOwner, ::whenTrackingAlreadyExists)
        viewModel.hasError.observe(viewLifecycleOwner, ::whenGetTrackingHasError)
    }

    private fun initUI() {
        setInsertAndRefreshClickListener()
        initRecyclerView()
        initAD()
        initObservers()
        getAllTrackingInDataBase()
    }

    private fun initRecyclerView() {
        binding.rvItensEmAndamento.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = trackingAdapterInProgress
        }
        binding.includeLayoutSessaoConcluidos.rvItemsConcluidos.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = trackingAdapterCompleted
        }
    }
    private fun setInsertAndRefreshClickListener() {
        binding.btnAdd.setOnClickListener {
            openInsertTrackingFragment()
        }
        binding.btnRefresh.setOnClickListener {
            reloadAllTrackingInProgress()
        }
    }

    private fun reloadAllTrackingInProgress() {
        viewModel.reload()
    }

    private fun getTracking(codigo: String, name: String?) {
        viewModel.getTracking(codigo, name)
    }

    private fun getAllTrackingInDataBase() {
        viewModel.getAllTrackingInDataBase()
    }

    private fun whenHasTrackingInProgress(trackingList: List<TrackingModel>) {
        binding.rvItensEmAndamento.visibility = View.VISIBLE
        binding.includeLayoutEmptyState.layoutEmptyState.visibility = View.GONE
        trackingAdapterInProgress.submitList(trackingList)
    }

    private fun whenHasTrackingCompleted(trackingList: List<TrackingModel>) {
        with(binding.includeLayoutSessaoConcluidos) {
            layoutSessaoConcluidos.visibility = View.VISIBLE
            trackingAdapterCompleted.submitList(trackingList)
        }
    }

    private fun whenTrackingAlreadyExists(show: Boolean) {
        if(show) {
            showSnackbar(binding.root, getString(R.string.error_rastreio_ja_inserido))
        }
    }

    private fun whenGetTrackingHasError(hasError: Boolean) {
        if (hasError) {
            showErrorSnackbar(binding.root, getString(R.string.error_server))
        }
    }

    private fun openInsertTrackingFragment() {
        val modalBottomSheet = InsertTrackingBottomSheetFragment(this@MainFragment)
        modalBottomSheet.showNow(parentFragmentManager, InsertTrackingBottomSheetFragment.TAG)
    }

    override fun onItemClick(tracking: TrackingModel) {
        val modalBottomSheet = TrackingDetailsBottomSheetFragment(tracking = tracking, this)
        modalBottomSheet.showNow(parentFragmentManager, TrackingDetailsBottomSheetFragment.TAG)
    }

    override fun sendTrackingCode(code: String, name: String?) {
        getTracking(code, name)
    }

    override fun launch() {
        getAllTrackingInDataBase()
    }
}