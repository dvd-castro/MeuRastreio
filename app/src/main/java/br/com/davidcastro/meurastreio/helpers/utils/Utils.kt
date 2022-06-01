package br.com.davidcastro.meurastreio.helpers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.helpers.extensions.toJsonString
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getDaysBetweenDatesStrings(firstEvent: EventosModel, lastEvent: EventosModel): Int {
    var days = 0
    var firstEventDate: DateTime? = null
    var lastEventDate: DateTime? = null
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")

    try {
        firstEventDate = DateTime.parse(firstEvent.data, formatter)

        lastEventDate = if(lastEvent.status == "Objeto entregue ao destinatário") {
            DateTime.parse(lastEvent.data, formatter)
        } else {
            DateTime()
        }

        days = Days.daysBetween(firstEventDate, lastEventDate).days

    } catch (ex: Exception) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)

        crashlytics.setCustomKeys {
            key("firstEvent", firstEvent.toJsonString())
            key("lastEvent", lastEvent.toJsonString())
            key("firstEventDate", firstEventDate.toString())
            key("lastEventDate", lastEventDate.toJsonString())
            key("days", days)
        }

        crashlytics.recordException(ex)
    }

    return days
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