package br.com.davidcastro.meurastreio.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.davidcastro.meurastreio.data.model.TrackingHome

@Entity(tableName = "rastreio")
data class TrackingEntity (
    @PrimaryKey
    var name: String,
    var code: String,
    var lastStatus: String,
    var local: String,
    var date: String,
    var hasUpdated: Boolean,
    var hasCompleted: Boolean,
) {
    fun toTrackingHome(): TrackingHome =
        TrackingHome( name, code, lastStatus, local, date, hasUpdated, hasCompleted )
}