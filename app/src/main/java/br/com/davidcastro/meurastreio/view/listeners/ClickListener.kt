package br.com.davidcastro.meurastreio.view.listeners

import br.com.davidcastro.data.model.TrackingModel

interface ClickListener {
    fun onItemClick(tracking: TrackingModel)
}