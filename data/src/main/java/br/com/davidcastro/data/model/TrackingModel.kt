package br.com.davidcastro.data.model

import android.os.Parcelable
import br.com.davidcastro.data.db.entity.TrackingEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class EventList: ArrayList<Evento>()

class TrackingList(list: List<TrackingModel>): ArrayList<TrackingModel>() {

    init {
        this.addAll(list)
    }

    fun getAllTrackingCompleted(): List<TrackingModel> = this.filter { it.hasCompleted }
    fun getAllTrackingInProgress(): List<TrackingModel> = this.filter { !it.hasCompleted }
}

@Parcelize
data class TrackingModel(
    @SerializedName("codigo") val code: String,
    @SerializedName("eventos") val events: ArrayList<Evento>,
    var name: String?,
    var hasUpdated: Boolean = false,
    var hasCompleted: Boolean = false,
) : Parcelable {
    fun getLastEvent(): Evento = events.first()

    fun getLastEventDate(): String? = getLastEvent().date

    fun getLastEventDateAndHour(): String = "${getLastEventDate()} - ${getLastEvent().hour}"

    fun getEventDateAndLocal():String = "${getLastEvent().date} - ${getLastEvent().local}"

    fun toTrackingEntity() = TrackingEntity(
        codigo = this.code,
        nome = this.name ?: "",
        eventos = Gson().toJson(this.events),
        hasUpdated = this.hasUpdated,
        hasCompleted = this.hasCompleted,
    )

    fun getStatusToShare(): String {
        var status = "Codigo: $code \nStatus: ${getLastEvent().status}"
        getLastEvent().subStatus?.forEach {
            status += "\n$it"
        }
        return status
    }

}

@Parcelize
data class Evento(
    @SerializedName("data") val date: String?,
    @SerializedName("hora") val hour: String?,
    @SerializedName("local") val local: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("subStatus")  val subStatus: ArrayList<String>?
) : Parcelable {
    fun getEventDateAndHourAndLocal():String = "$date $hour - $local"
}