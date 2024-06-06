package br.com.davidcastro.meurastreio.domain.mappers

import br.com.davidcastro.meurastreio.core.utils.extensions.orFalse
import br.com.davidcastro.meurastreio.data.service.db.entity.TrackingEntity
import br.com.davidcastro.meurastreio.data.service.remote.response.TrackingResponse
import br.com.davidcastro.meurastreio.domain.model.EventDomain
import br.com.davidcastro.meurastreio.domain.model.EventList
import br.com.davidcastro.meurastreio.domain.model.TrackingDomain
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<TrackingResponse>.toDomain(): Flow<TrackingDomain> {
    return map { it.toDomain() }
}

fun TrackingResponse.toDomain(): TrackingDomain {
    return TrackingDomain(
        code = this.code,
        events = this.events.map {
            EventDomain(
                date = it.date,
                hour = it.hour,
                local = it.local,
                status = it.status,
                subStatus = it.subStatus
            )
        }
    )
}

fun TrackingEntity.toDomain(): TrackingDomain {
    return TrackingDomain(
        code = this.codigo,
        events = Gson().fromJson(this.eventos, EventList::class.java),
        name = this.nome,
        hasUpdated = this.hasUpdated,
        hasCompleted = this.hasCompleted
    )
}

fun TrackingDomain.toEntity() = TrackingEntity(
    codigo = this.code,
    nome = this.name.orEmpty(),
    eventos = Gson().toJson(this.events),
    hasUpdated = this.hasUpdated.orFalse(),
    hasCompleted = this.hasCompleted.orFalse(),
)