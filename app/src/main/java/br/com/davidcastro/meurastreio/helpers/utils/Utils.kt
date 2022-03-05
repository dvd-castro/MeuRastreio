package br.com.davidcastro.meurastreio.helpers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import br.com.davidcastro.meurastreio.data.model.EventosModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getDaysBetweenDatesStrings(firstEvent: EventosModel, lastEvent: EventosModel): Int {
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
    val firstEventDate = DateTime.parse(firstEvent.data, formatter)

    val lastEventDate = if(lastEvent.status == "Objeto entregue ao destinatário") {
        DateTime.parse(lastEvent.data, formatter)
    } else {
        DateTime()
    }

    return Days.daysBetween(firstEventDate, lastEventDate).days
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