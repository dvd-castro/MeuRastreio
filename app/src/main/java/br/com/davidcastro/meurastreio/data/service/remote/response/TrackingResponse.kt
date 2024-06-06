package br.com.davidcastro.meurastreio.data.service.remote.response

import com.google.gson.annotations.SerializedName

data class TrackingResponse(
    @SerializedName("codigo") val code: String,
    @SerializedName("eventos") val events: List<EventResponse>
)