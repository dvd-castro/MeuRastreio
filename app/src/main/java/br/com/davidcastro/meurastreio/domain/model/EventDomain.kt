package br.com.davidcastro.meurastreio.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class EventDomain(
    @SerializedName("data") val date: String?,
    @SerializedName("hora") val hour: String?,
    @SerializedName("local") val local: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("subStatus") val subStatus: ArrayList<String>?
) : Parcelable {
    fun getDateAndHour(): String {
        return "$date - $hour"
    }
}
