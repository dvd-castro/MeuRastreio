package br.com.davidcastro.ui.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import br.com.davidcastro.ui.R
import com.google.android.material.snackbar.Snackbar

object UiUtils {
    fun getTrackingStatusColor(context: Context, status: String): Int =
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

    fun showSnackbar(view: View, message: String) =
        Snackbar.make(view, message, 3000).show()

    fun showErrorSnackbar(view: View, message: String) {
        Snackbar
            .make(view, message, 5000)
            .setBackgroundTint(ContextCompat.getColor(view.context, R.color.red))
            .show()
    }
}