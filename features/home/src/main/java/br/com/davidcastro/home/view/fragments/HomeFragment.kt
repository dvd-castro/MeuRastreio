package br.com.davidcastro.home.view.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.home.BuildConfig
import br.com.davidcastro.home.R
import br.com.davidcastro.home.databinding.FragmentHomeBinding
import br.com.davidcastro.home.view.adapters.TrackingAdapter
import br.com.davidcastro.home.viewmodel.MainViewModel
import br.com.davidcastro.inserttracking.view.fragments.InsertTrackingBottomSheetFragment
import br.com.davidcastro.trackingdetails.listeners.OnCloseBottomSheetDialogFragment
import br.com.davidcastro.trackingdetails.view.fragments.TrackingDetailsBottomSheetFragment
import br.com.davidcastro.ui.utils.UiUtils.showErrorSnackbar
import br.com.davidcastro.ui.utils.UiUtils.showSnackbar
import com.google.android.gms.ads.AdRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(), OnCloseBottomSheetDialogFragment {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val trackingAdapterCompleted = TrackingAdapter(::onItemClick)
    private val trackingAdapterInProgress = TrackingAdapter(::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initAD() {
        if(!BuildConfig.DEBUG) {
            val adRequest = AdRequest.Builder().build()
            binding.adView.visibility = View.VISIBLE
            binding.adView2.visibility = View.VISIBLE
            binding.adView.loadAd(adRequest)
            binding.adView2.loadAd(adRequest)
        }
    }

    private fun initObservers() {
        viewModel.trackingInProgress.observe(viewLifecycleOwner, ::whenHasTrackingInProgress)
        viewModel.trackingCompleted.observe(viewLifecycleOwner, ::whenHasTrackingCompleted)
        viewModel.trackingAlreadyExists.observe(viewLifecycleOwner, ::whenTrackingAlreadyExists)
        viewModel.hasError.observe(viewLifecycleOwner, ::whenGetTrackingHasError)
        viewModel.loader.observe(viewLifecycleOwner, ::showLoader)
    }

    private fun initUI() {
        setInsertAndRefreshClickListener()
        initRecyclerView()
        initAD()
        initObservers()
        getAllTrackingInDataBase()
        setNotificationsChannel()
        requestNotificationPermission()
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
        if(trackingList.isNotEmpty()) {
            binding.rvItensEmAndamento.visibility = View.VISIBLE
            binding.includeLayoutEmptyState.layoutEmptyState.visibility = View.GONE
        } else {
            binding.rvItensEmAndamento.visibility = View.GONE
            binding.includeLayoutEmptyState.layoutEmptyState.visibility = View.VISIBLE
        }

        trackingAdapterInProgress.submitList(trackingList)
    }

    private fun whenHasTrackingCompleted(trackingList: List<TrackingModel>) {
        with(binding.includeLayoutSessaoConcluidos) {

            if(trackingList.isNotEmpty()) {
                layoutSessaoConcluidos.visibility = View.VISIBLE
            } else {
                layoutSessaoConcluidos.visibility = View.GONE
            }

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
        val modalBottomSheet = InsertTrackingBottomSheetFragment(::sendTrackingCode)
        modalBottomSheet.showNow(parentFragmentManager, InsertTrackingBottomSheetFragment.TAG)
    }

    private fun showLoader(show: Boolean) {
        if(show) {
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.GONE
        }
    }

    private fun setItemAsUpdatedFalse(tracking: TrackingModel) {
        if(tracking.hasUpdated) {
            tracking.hasUpdated = false
            viewModel.insertNewTracking(tracking, tracking.name)
        }
    }

    private fun onItemClick(tracking: TrackingModel) {
        setItemAsUpdatedFalse(tracking)
        TrackingDetailsBottomSheetFragment(tracking = tracking, this).showNow(parentFragmentManager, TrackingDetailsBottomSheetFragment.TAG)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun setNotificationsChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(getString(R.string.notification_channel_id), name, importance).apply {
                description = descriptionText
                setShowBadge(true)
            }

            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendTrackingCode(code: String, name: String?) {
        getTracking(code, name)
    }

    override fun launch() {
        getAllTrackingInDataBase()
    }
}