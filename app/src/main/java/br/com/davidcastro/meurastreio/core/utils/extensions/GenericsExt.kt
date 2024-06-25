package br.com.davidcastro.meurastreio.core.utils.extensions

import android.net.Uri
import com.google.gson.Gson

fun <T> T.toStringArgs(): String = Uri.encode(Gson().toJson(this))