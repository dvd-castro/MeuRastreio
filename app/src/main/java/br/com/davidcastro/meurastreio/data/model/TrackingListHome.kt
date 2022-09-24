package br.com.davidcastro.meurastreio.data.model

import br.com.davidcastro.meurastreio.data.db.entity.TrackingEntity

class TrackingListHome: ArrayList<TrackingHome>()

data class TrackingHome(
    var name: String?,
    var code: String?,
    var lastStatus: String?,
    var local: String?,
    var date: String?,
    var hasUpdated: Boolean?,
    var hasCompleted: Boolean?,
) {
    fun toTrackingEntity(): TrackingEntity =
        TrackingEntity(
            name ?: "",
            code ?: "",
            lastStatus ?: "",
            local ?: "",
            date ?: "",
            hasUpdated ?: false ,
            hasCompleted ?: false
        )
}