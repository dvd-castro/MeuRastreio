package br.com.davidcastro.meurastreio.helpers.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

fun getDaysBetweenDatesStrings(startDate: String, endDate: String): Int{
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val date1 = formatter.parse(startDate).time
    val date2 = formatter.parse(endDate).time
    val duration = (date1 - date2) / (1000 * 60 * 60 * 24)

    return duration.toInt()
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
    }
}