package br.com.davidcastro.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.davidcastro.data.model.EventList
import br.com.davidcastro.data.model.TrackingModel
import com.google.gson.Gson

@Entity(tableName = "rastreio")
data class TrackingEntity (
    @PrimaryKey
    var codigo : String = "",
    var nome : String = "",
    var eventos : String = "",
    var hasUpdated: Boolean? = false,
    var hasCompleted: Boolean? = false,
) {
    fun toTrackingModel(): TrackingModel =
        TrackingModel(
            code = this.codigo,
            events = Gson().fromJson(this.eventos, EventList::class.java),
            name = this.nome,
            hasUpdated = this.hasUpdated ?: false,
            hasCompleted = this.hasCompleted ?: false
        )
}