package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.core.utils.Dimens
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Loading(
    enabled: Boolean = false
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    if(enabled) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(GetPrimaryColor()).fillMaxSize()
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(Dimens.dimen200dp)
            )
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    Loading(true)
}