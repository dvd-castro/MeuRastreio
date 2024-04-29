package br.com.davidcastro.meurastreio.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class EventList: ArrayList<EventDomain>()

@Parcelize
data class TrackingDomain(
    var code: String = "",
    var events: List<EventDomain> = listOf(),
    var name: String? = null,
    var hasUpdated: Boolean? = false,
    var hasCompleted: Boolean? = false,
) : Parcelable {
    fun getLastEvent(): EventDomain = events.first()

    fun getLastEventDate(): String? = getLastEvent().date

    fun getEventDateAndLocal():String = "${getLastEvent().date} - ${getLastEvent().local}"

    fun getStatusToShare(): String {
        var status = "Codigo: $code \nStatus: ${getLastEvent().status}"
        getLastEvent().subStatus?.forEach {
            status += "\n$it"
        }
        return status
    }
}
