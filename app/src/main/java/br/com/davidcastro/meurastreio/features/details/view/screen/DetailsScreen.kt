package br.com.davidcastro.meurastreio.features.details.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.core.theme.Red
import br.com.davidcastro.meurastreio.core.utils.Dimens
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import br.com.davidcastro.meurastreio.features.details.view.components.DetailsToolbar
import br.com.davidcastro.meurastreio.features.details.view.components.IconButtonComponent
import br.com.davidcastro.meurastreio.features.details.viewmodel.DetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    isFromResult: Boolean,
    tracking: TrackingDomain,
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = koinViewModel()
) {
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
            isFromResult = isFromResult
        )
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    isFromResult: Boolean,
    tracking: TrackingDomain,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = Dimens.dimen16dp)
    ) {
        Text(
            text = stringResource(R.string.title_code),
            fontSize = Dimens.size18sp,
            modifier = Modifier.padding(bottom = Dimens.dimen8dp)
        )

        Text(
            text = tracking.code,
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.size22sp
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.dimen28dp,
                    end = Dimens.dimen28dp,
                    start = Dimens.dimen28dp,
                )
        ) {
            IconButtonComponent(
                color = Color.Black,
                title = stringResource(id = R.string.action_to_share),
                icon = Icons.Filled.Share
            ) {

            }

            Spacer(modifier = Modifier.width(Dimens.dimen52dp))

            if(isFromResult) {
                IconButtonComponent(
                    color = Color.Black,
                    title = stringResource(id = R.string.action_to_save),
                    icon = Icons.Outlined.Bookmark
                ) {

                }
            } else {
                IconButtonComponent(
                    color = Red,
                    title = stringResource(id = R.string.action_to_delete),
                    icon = Icons.Filled.Delete
                ) {

                }
            }
        }
    }
}

@Preview(backgroundColor = 0xFFEDEDF0, showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailsContent(
        isFromResult = true,
        tracking = TrackingDomain(
            code = "OV210496545BR",
            hasCompleted = false,
            name = "Shein"
        )
    )
}