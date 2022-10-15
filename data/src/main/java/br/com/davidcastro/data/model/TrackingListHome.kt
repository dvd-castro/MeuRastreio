package br.com.davidcastro.data.model

import br.com.davidcastro.data.db.entity.TrackingEntity

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