package br.com.davidcastro.meurastreio.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rastreio")
data class RastreioEntity (
    @PrimaryKey
    val codigo : String,
    val nome : String,
    val eventos : String
)