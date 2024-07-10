package br.com.davidcastro.meurastreio.features.details.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.davidcastro.meurastreio.commons.utils.Dimens

@Composable
fun IconButtonComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    color: Color,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(
                AbsoluteRoundedCornerShape(Dimens.dimen8dp)
            )
            .background(backgroundColor)
            .padding(Dimens.dimen8dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(Dimens.dimen28dp),
            tint = color,
            contentDescription = null
        )

        Text(
            text = title,
            fontSize = Dimens.size18sp,
            color = color,
            modifier = Modifier.padding(start = Dimens.dimen8dp)
        )
    }
}