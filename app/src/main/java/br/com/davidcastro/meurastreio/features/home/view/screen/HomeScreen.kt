package br.com.davidcastro.meurastreio.features.home.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.core.utils.Dimens
import br.com.davidcastro.meurastreio.domain.model.StateEnum
import br.com.davidcastro.meurastreio.features.home.mvi.HomeAction
import br.com.davidcastro.meurastreio.features.home.view.components.ErrorDialog
import br.com.davidcastro.meurastreio.features.home.view.components.HomeFilter
import br.com.davidcastro.meurastreio.features.home.view.components.HomeToolbar
import br.com.davidcastro.meurastreio.features.home.view.components.Loading
import br.com.davidcastro.meurastreio.features.home.view.components.TrackingCardList
import br.com.davidcastro.meurastreio.features.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val items = StateEnum.entries.map { it.value }
    val uiState = homeViewModel.uiState.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        homeViewModel.dispatch(HomeAction.GetAllTracking)
    }

    Scaffold(
        containerColor = GetSecondaryColor(),
        topBar = {
            HomeToolbar(
                onReload = {
                    homeViewModel.dispatch(HomeAction.ReloadAllTracking)
                },
                onSearch = {
                    keyboardController?.hide()
                    homeViewModel.dispatch(HomeAction.GetTracking(it))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = Dimens.dimen16dp)
                .fillMaxWidth()
        ) {
            HomeFilter(items = items) {
                homeViewModel.dispatch(HomeAction.UpdateTrackingFilter(it))
            }

            TrackingCardList(
                list = uiState.currentSelectedFilter
            )
        }
    }

    if(uiState.hasError) {
        ErrorDialog(
            onDismissRequest = {
                homeViewModel.dispatch(HomeAction.ShowError(false))
            }
        )
    }

    Loading(
        enabled = uiState.hasLoading
    )
}