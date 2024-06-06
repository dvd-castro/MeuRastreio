package br.com.davidcastro.meurastreio.data.service.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rastreio")
data class TrackingEntity (
    @PrimaryKey
    var codigo : String = "",
    var nome : String = "",
    var eventos : String = "",
    var hasUpdated: Boolean = false,
    var hasCompleted: Boolean = false,
)