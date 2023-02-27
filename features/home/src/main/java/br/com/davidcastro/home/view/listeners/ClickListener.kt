package br.com.davidcastro.home.view.listeners

import br.com.davidcastro.data.model.TrackingModel

interface ClickListener {
    fun onItemClick(tracking: TrackingModel)
}