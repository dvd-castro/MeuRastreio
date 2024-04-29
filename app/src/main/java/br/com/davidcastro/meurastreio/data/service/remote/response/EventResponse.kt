package br.com.davidcastro.meurastreio.data.service.remote.response

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("data") val date: String?,
    @SerializedName("hora") val hour: String?,
    @SerializedName("local") val local: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("subStatus")  val subStatus: ArrayList<String>?
)