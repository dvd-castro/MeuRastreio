package br.com.davidcastro.meurastreio.helpers.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, 3000).show()
}