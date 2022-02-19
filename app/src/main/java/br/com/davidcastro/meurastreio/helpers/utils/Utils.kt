package br.com.davidcastro.meurastreio.helpers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import br.com.davidcastro.meurastreio.data.model.EventosModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

fun getDaysBetweenDatesStrings(firstEvent: EventosModel, lastEvent: EventosModel): Int {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val date1 = formatter.parse(firstEvent.data).time

    val date2: Long = if(firstEvent.status == "Objeto entregue ao destinatário") {
        formatter.parse(lastEvent.data).time
    }else {
        Date().time
    }

    return ((date2 - date1) / (1000 * 60 * 60 * 24)).toInt()
}

class NetworkUtils {
    companion object {

        fun <S> getRetrofitInstance(serviceClass: Class<S>, path: String) : S {
            val retrofit = Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(serviceClass)
        }

        fun hasConnectionActive(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}