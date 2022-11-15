package br.com.davidcastro.data.model

import br.com.davidcastro.data.db.entity.TrackingEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class EventList: ArrayList<Evento>()

class TrackingList(list: List<TrackingModel>): ArrayList<TrackingModel>() {

    init {
        this.addAll(list)
    }

    fun getAllTrackingCompleted(): List<TrackingModel> = this.filter { it.hasCompleted }
    fun getAllTrackingInProgress(): List<TrackingModel> = this.filter { !it.hasCompleted }
}

data class TrackingModel(
    @SerializedName("codigo") val code: String,
    @SerializedName("eventos") val events: ArrayList<Evento>,
    var name: String?,
    var hasUpdated: Boolean = false,
    var hasCompleted: Boolean = false,
) {
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
}

data class Evento(
    @SerializedName("data") val date: String?,
    @SerializedName("hora") val hour: String?,
    @SerializedName("local") val local: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("subStatus")  val subStatus: ArrayList<String>?
)