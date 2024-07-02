package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.theme.Red
import br.com.davidcastro.meurastreio.core.utils.Dimens

data class ErrorMessage(
    val title: String = "",
    val body: String = ""
)

@Composable
fun ErrorDialog(
    errorMessage: ErrorMessage,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        containerColor = GetPrimaryColor(),
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier.size(Dimens.dimen52dp),
                tint = Red
            )
        },
        title = {
            Text(text = errorMessage.title)
        },
        text = {
            Text(text = errorMessage.body)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.action_ok),
                modifier = Modifier.clickable {
                    onDismissRequest()
                }
            )
        }
    )
}