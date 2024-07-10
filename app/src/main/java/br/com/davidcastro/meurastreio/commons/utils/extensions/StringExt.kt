package br.com.davidcastro.meurastreio.commons.utils.extensions

import androidx.compose.runtime.Composable

@Composable
fun String?.IfNotNullOrEmpty(
    content: @Composable (String) -> Unit
) {
    if(!this.isNullOrEmpty()) {
        content(this)
    }
}

inline fun String?.or(defaultValue: () -> String): String {
    return this ?: defaultValue()
}