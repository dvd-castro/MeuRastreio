package br.com.davidcastro.meurastreio.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Red = Color(0xFFC24343)

@Composable
fun GetPrimaryColor(): Color {
    return if(isSystemInDarkTheme())
        Color.Black
    else
        Color.White
}

@Composable
fun GetSecondaryColor(): Color {
    return if(isSystemInDarkTheme())
        Color(0xFF3A3B3D)
    else
        Color(0xFFEDEDF0)
}

@Composable
fun GetCardBackgroundColor(): Color {
    return if(isSystemInDarkTheme())
        Color(0xFF202123)
    else
        Color.White
}

@Composable
fun GetFontColor(): Color {
    return if(isSystemInDarkTheme())
        Color.White
    else
        Color.Black
}