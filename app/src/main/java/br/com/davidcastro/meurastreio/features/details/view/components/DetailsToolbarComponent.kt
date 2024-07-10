package br.com.davidcastro.meurastreio.features.details.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.commons.utils.Dimens.dimen16dp
import br.com.davidcastro.meurastreio.commons.utils.Dimens.dimen52dp
import br.com.davidcastro.meurastreio.commons.utils.Dimens.size16sp
import br.com.davidcastro.meurastreio.commons.utils.Dimens.size18sp
import br.com.davidcastro.meurastreio.core.theme.GetFontColor
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor

@Composable
fun DetailsToolbar(
    title: String,
    onBackPress: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(GetPrimaryColor())
            .height(dimen52dp)
            .fillMaxWidth()
            .padding(horizontal = dimen16dp)
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    onBackPress()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = GetFontColor()
            )
            Text(
                text = stringResource(R.string.action_go_bak),
                color = GetFontColor(),
                fontSize = size16sp
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = size18sp
        )
    }
}

@Preview
@Composable
private fun DetailsToolbarPreview() {
    DetailsToolbar(stringResource(id = R.string.title_result)) {}
}