package br.com.davidcastro.meurastreio.commons.utils.extensions

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson

inline fun <reified T> NavBackStackEntry.getArgs(key: String): T? {
    return arguments?.get(key) as? T
}

inline fun <reified T> NavBackStackEntry.getArgsModel(key: String): T? {
    return Gson().fromJson(Uri.decode(arguments?.getString(key)), T::class.java)
}