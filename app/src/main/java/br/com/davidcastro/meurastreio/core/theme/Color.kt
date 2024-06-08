package br.com.davidcastro.meurastreio.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val White = Color(0xFFFFFBFE)
val Gray = Color(0xFF323C42)
val Red = Color(0xFFC24343)

@Composable
fun GetPrimaryColor(): Color {
    return if(isSystemInDarkTheme())
        Color.Black
    else
        White
}

@Composable
fun GetSecondaryColor(): Color {
    return if(isSystemInDarkTheme())
        Color(0xFF1C1C1E)
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