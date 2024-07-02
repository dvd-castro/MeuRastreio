package br.com.davidcastro.meurastreio.features.home.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.navigation.Routes
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.core.utils.Dimens
import br.com.davidcastro.meurastreio.core.utils.extensions.toStringArgs
import br.com.davidcastro.meurastreio.domain.model.StateEnum
import br.com.davidcastro.meurastreio.features.home.mvi.ErrorType
import br.com.davidcastro.meurastreio.features.home.mvi.HomeAction
import br.com.davidcastro.meurastreio.features.home.mvi.HomeResult
import br.com.davidcastro.meurastreio.features.home.view.components.ErrorDialog
import br.com.davidcastro.meurastreio.features.home.view.components.ErrorMessage
import br.com.davidcastro.meurastreio.features.home.view.components.HomeFilter
import br.com.davidcastro.meurastreio.features.home.view.components.HomeToolbar
import br.com.davidcastro.meurastreio.features.home.view.components.Loading
import br.com.davidcastro.meurastreio.features.home.view.components.TrackingCardList
import br.com.davidcastro.meurastreio.features.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val items = StateEnum.entries.map { it.value }
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState = homeViewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        homeViewModel.dispatch(
            HomeAction.GetAllTracking
        )
    }

    LaunchedEffect(homeViewModel.result) {
        homeViewModel.result.collectLatest { result ->
            when(result) {
                is HomeResult.NavigateTo -> navController.navigate(result.route)
            }
        }
    }

    Scaffold(
        containerColor = GetSecondaryColor(),
        topBar = {
            HomeToolbar(
                onReload = {
                    homeViewModel.dispatch(
                        HomeAction.ReloadAllTracking
                    )
                },
                onSearch = {
                    keyboardController?.hide()
                    homeViewModel.dispatch(
                        HomeAction.CheckIfHasAlreadyInserted(
                            code = it
                        )
                    )
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
                homeViewModel.dispatch(
                    HomeAction.UpdateTrackingFilter(
                        filter = it
                    )
                )
            }

            TrackingCardList(
                list = uiState.currentSelectedFilter
            ) {
                homeViewModel.dispatch(
                    HomeAction.NavigateTo(
                        Routes.DetailScreen(
                            tracking = it.toStringArgs(),
                            isFromResult = false
                        )
                    )
                )
            }
        }
    }

    Loading(enabled = uiState.hasLoading)

    ShowError(errorType = uiState.hasError) {
        homeViewModel.dispatch(
            HomeAction.ShowError(
                errorType = ErrorType.NONE
            )
        )
    }
}

@Composable
private fun ShowError(
    errorType: ErrorType,
    onDismiss: () -> Unit
) {
    if(errorType != ErrorType.NONE) {
        ErrorDialog(
            errorMessage = getErrorMessage(errorType),
            onDismissRequest = {
                onDismiss()
            }
        )
    }
}

@Composable
private fun getErrorMessage(errorType: ErrorType): ErrorMessage {
    return when (errorType) {
        ErrorType.ErrorGetInDb -> {
            ErrorMessage(
                title = stringResource(R.string.error_title_ops_we_have_a_problem),
                body = stringResource(R.string.error_body_dont_load_data)
            )
        }

        ErrorType.ErrorGetInRemote -> {
            ErrorMessage(
                title = stringResource(R.string.error_title_ops_we_have_a_error),
                body = stringResource(R.string.error_body_general_error)
            )
        }

        ErrorType.ErrorHasAlreadyInserted -> {
            ErrorMessage(
                title = stringResource(R.string.error_title_ops_be_atention),
                body = stringResource(R.string.error_body_code_has_already_inserted)
            )
        }

        else -> ErrorMessage()
    }
}