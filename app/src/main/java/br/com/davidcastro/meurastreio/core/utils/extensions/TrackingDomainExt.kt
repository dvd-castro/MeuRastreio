package br.com.davidcastro.meurastreio.core.utils.extensions

import br.com.davidcastro.meurastreio.domain.model.TrackingDomain

fun List<TrackingDomain>.getAllTrackingCompleted(): List<TrackingDomain> = this.filter { it.hasCompleted == true }

fun List<TrackingDomain>.getAllTrackingInProgress(): List<TrackingDomain> = this.filter { it.hasCompleted == false }