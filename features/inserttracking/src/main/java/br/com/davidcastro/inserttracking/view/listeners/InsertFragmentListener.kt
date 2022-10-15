package br.com.davidcastro.inserttracking.view.listeners

interface InsertFragmentListener {
    fun sendTrackingCode(code: String, name: String?)
}