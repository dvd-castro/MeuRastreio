package br.com.davidcastro.meurastreio.data.model

import com.google.gson.annotations.SerializedName

data class EventosModel (
    @field:SerializedName("data") val data: String,
    @field:SerializedName("hora") val hora: String,
    @field:SerializedName("local") val local: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("subStatus") val subStatus: List<String>,
) {
    val getStatus : String
        get() = "$data - $local"
}