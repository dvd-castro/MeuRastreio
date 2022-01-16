package br.com.davidcastro.meurastreio.data.model

import br.com.davidcastro.meurastreio.helpers.utils.getDaysBetweenDatesStrings
import com.google.gson.annotations.SerializedName

data class RastreioModel (
    @field:SerializedName("nome") val nome: String?,
    @field:SerializedName("codigo") val codigo: String,
    @field:SerializedName("eventos") val eventos: List<EventosModel>,
){
        val getDuracao : String
                get() {
                    val startDate = eventos.first().data
                    val endDate = eventos.last().data

                    return "${getDaysBetweenDatesStrings(startDate, endDate)} dias"
                }
}