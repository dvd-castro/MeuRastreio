package br.com.davidcastro.meurastreio.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class EventDomain(
    val date: String?,
    val hour: String?,
    val local: String?,
    val status: String?,
    val subStatus: ArrayList<String>?
) : Parcelable
