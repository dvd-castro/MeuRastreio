package br.com.davidcastro.meurastreio.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDomain(
    val date: String?,
    val hour: String?,
    val local: String?,
    val status: String?,
    val subStatus: ArrayList<String>?
) : Parcelable {
    fun getEventDateAndHourAndLocal():String = "$date $hour - $local"
}
