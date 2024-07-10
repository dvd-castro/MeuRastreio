package br.com.davidcastro.meurastreio.features.details.view.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.commons.components.TrackingCard
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.core.theme.Red
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsAction
import br.com.davidcastro.meurastreio.features.details.mvi.DetailsResult
import br.com.davidcastro.meurastreio.features.details.view.components.DetailsToolbar
import br.com.davidcastro.meurastreio.features.details.view.components.IconButtonComponent
import br.com.davidcastro.meurastreio.features.details.view.components.SetTrackingNameDialog
import br.com.davidcastro.meurastreio.features.details.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    isFromResult: Boolean,
    tracking: TrackingDomain,
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = koinViewModel()
) {
    val uiState = detailsViewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        containerColor = GetSecondaryColor(),
        topBar = {
            DetailsToolbar(
                title = if(isFromResult) stringResource(R.string.title_result) else tracking.name.orEmpty()
            ) {
                navController.popBackStack()
            }
        }
    ) { padding ->
        DetailsContent(
            modifier = Modifier.padding(padding),
            tracking = tracking,
            isFromResult = isFromResult,
            navController = navController,
            detailsViewModel = detailsViewModel
        )
    }

    if(uiState.showSetNameDialog) {
        SetTrackingNameDialog { name ->
            name?.let {
                detailsViewModel.dispatch(
                    DetailsAction.SaveTracking(
                        trackingDomain = tracking.copy(name = it)
                    )
                )
            } ?: run {
                detailsViewModel.dispatch(
                    DetailsAction.ShowSetNameDialog(false)
                )
            }
        }
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    isFromResult: Boolean,
    tracking: TrackingDomain,
    navController: NavHostController,
    detailsViewModel: DetailsViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(detailsViewModel.result) {
        detailsViewModel.result.collectLatest {
            when(it) {
                is DetailsResult.ExitScreen -> {
                    navController.popBackStack()
                }

                is DetailsResult.ShareTracking -> {
                    shareLastEvent(
                        context = context,
                        tracking = tracking
                    )
                }
            }
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .scrollable(
                enabled = true,
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
    ) {
        item {
            Text(
                text = stringResource(R.string.title_code),
                fontSize = Dimens.size18sp,
                modifier = Modifier.padding(
                    bottom = Dimens.dimen8dp,
                    top = Dimens.dimen16dp
                )
            )

            SelectionContainer {
                Text(
                    text = tracking.code,
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.size22sp
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.dimen28dp,
                        end = Dimens.dimen28dp,
                        start = Dimens.dimen28dp,
                        bottom = Dimens.dimen16dp
                    )
            ) {
                IconButtonComponent(
                    color = Color.Black,
                    title = stringResource(id = R.string.action_to_share),
                    icon = Icons.Filled.Share
                ) {
                    detailsViewModel.dispatch(
                        DetailsAction.ShareTracking(tracking)
                    )
                }

                Spacer(modifier = Modifier.width(Dimens.dimen52dp))

                if(isFromResult) {
                    IconButtonComponent(
                        color = Color.Black,
                        title = stringResource(id = R.string.action_to_save),
                        icon = Icons.Outlined.Bookmark
                    ) {
                        detailsViewModel.dispatch(
                            DetailsAction.ShowSetNameDialog(true)
                        )
                    }
                } else {
                    IconButtonComponent(
                        color = Red,
                        title = stringResource(id = R.string.action_to_delete),
                        icon = Icons.Filled.Delete
                    ) {
                        detailsViewModel.dispatch(
                            DetailsAction.DeleteTracking(tracking.code)
                        )
                    }
                }
            }
        }

        items(tracking.events.orEmpty()) {
            TrackingCard(
                modifier = Modifier.padding(
                    bottom = Dimens.dimen16dp,
                    start = Dimens.dimen16dp,
                    end = Dimens.dimen16dp
                ),
                status = it.status.orEmpty(),
                local = it.subStatus.orEmpty(),
                date = it.date.orEmpty()
            )
        }
    }
}

private fun shareLastEvent(context: Context, tracking: TrackingDomain) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, tracking.getStatusToShare())
        type = "text/plain"
    }

    context.startActivity(Intent.createChooser(sendIntent, null))
}