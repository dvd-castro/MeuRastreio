package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.core.utils.Dimens

@Composable
fun HomeToolbar(
    onSearch: (String) -> Unit,
    onReload: () -> Unit
) {
    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Row(
        modifier = Modifier
            .background(GetPrimaryColor())
            .height(Dimens.dimen140dp)
            .padding(Dimens.dimen16dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = Dimens.dimen8dp)
            ) {
                Text(
                    text = "Meu Rastreio",
                    fontSize = Dimens.size22sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { onReload() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                    )
                }
            }

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                singleLine = true,
                shape = ShapeDefaults.Medium,
                textStyle = TextStyle.Default.copy(
                    fontSize = Dimens.size16sp,
                    lineHeight = Dimens.size14sp,
                ),
                modifier = Modifier
                    .height(Dimens.dimen52dp)
                    .fillMaxWidth()
                    .padding(Dimens.dimen0dp),
                placeholder = {
                    Text(
                        text = "Ex: QQ870214897BR",
                        fontSize = Dimens.size16sp,
                        textAlign = TextAlign.Center,
                        lineHeight = Dimens.size14sp,
                        color = Color.Gray
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSearch(inputText.text)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GetSecondaryColor(),
                    unfocusedContainerColor = GetSecondaryColor(),
                    disabledContainerColor = GetSecondaryColor(),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}