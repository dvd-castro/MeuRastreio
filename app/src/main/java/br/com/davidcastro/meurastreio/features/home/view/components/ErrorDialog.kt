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
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.theme.Red
import br.com.davidcastro.meurastreio.core.utils.Dimens

@Composable
fun ErrorDialog(
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
            Text(text = "Código não encontrado")
        },
        text = {
            Text(text = "Verifique o código, e tente novamente.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Text(
                text = "Ok, entendi",
                modifier = Modifier.clickable {
                    onDismissRequest()
                }
            )
        }
    )
}