package br.com.davidcastro.ui.utils

import android.content.Context
import br.com.davidcastro.ui.R

object UiUtils {
    fun getColor(context: Context, status: String): Int =
        when(status) {
            context.getString(R.string.status_encaminhado) -> {
                context.getColor(R.color.blue)
            }
            context.getString(R.string.status_entregue) -> {
                context.getColor(R.color.green)
            }
            else -> {
                context.getColor(R.color.orange)
            }
        }
}