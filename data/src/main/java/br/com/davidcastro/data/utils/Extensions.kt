package br.com.davidcastro.data.utils

import br.com.davidcastro.data.db.entity.TrackingEntity
import br.com.davidcastro.data.model.TrackingModel

fun List<TrackingEntity>.toTrackingModelList(): List<TrackingModel> = this.map { it.toTrackingModel() }

fun List<TrackingModel>.getAllTrackingCompleted(): List<TrackingModel> = this.filter { it.hasCompleted }

fun List<TrackingModel>.getAllTrackingInProgress(): List<TrackingModel> = this.filter { !it.hasCompleted }