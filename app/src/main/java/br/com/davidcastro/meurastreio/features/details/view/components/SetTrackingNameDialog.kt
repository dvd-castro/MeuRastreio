package br.com.davidcastro.meurastreio.features.details.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.core.theme.GetFontColor
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor

@Composable
fun SetTrackingNameDialog(
    onDismissRequest: (name: String?) -> Unit,
) {
    var hasError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val message: String = stringResource(R.string.error_type_a_name)
    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    AlertDialog(
        containerColor = GetPrimaryColor(),
        onDismissRequest = {
            onDismissRequest(null)
        },
        title = {
            Text(text = stringResource(R.string.title_add_name_save_tracking_dialog))
        },
        text = {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.text_field_placeholder_save_tracking),
                        color = Color.Gray
                    )
                },
                isError = hasError,
                supportingText = {
                    Text(text = errorMessage)
                },
                shape = ShapeDefaults.Medium,
                textStyle = TextStyle.Default.copy(
                    fontSize = Dimens.size16sp,
                    lineHeight = Dimens.size14sp,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GetSecondaryColor(),
                    unfocusedContainerColor = GetSecondaryColor(),
                    disabledContainerColor = GetSecondaryColor(),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = GetFontColor()
                ),
                onClick = {
                    if(inputText.text.isBlank() || inputText.text.isEmpty()) {
                        errorMessage = message
                        hasError = true
                    } else {
                        onDismissRequest(inputText.text)
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.action_to_save)
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = GetPrimaryColor()
                ),
                onClick = {
                    onDismissRequest(null)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.action_to_cancel),
                    color = GetFontColor()
                )
            }
        }
    )
}