package br.com.davidcastro.meurastreio.helpers.extensions

import br.com.davidcastro.meurastreio.data.db.entity.RastreioEntity
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


//GSON EXTENSION

val gson: Gson = GsonBuilder().create()

inline fun <reified T> T.toJsonString(): String = gson.toJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> String.fromJson(): T = gson.fromJson(this, object : TypeToken<T>() {}.type)


//RASTREIO EXTENSION

fun RastreioEntity.toRastreioModel() : RastreioModel {
    val listaEventos = this.eventos.fromJson<List<EventosModel>>()

    return  RastreioModel(
        nome = this.nome,
        codigo = this.codigo,
        eventos = listaEventos
    )
}

fun RastreioModel.toRastreioEntity() : RastreioEntity {

    return RastreioEntity(
        nome = this.nome?: "Encomenda",
        codigo = this.codigo,
        eventos = this.eventos.toJsonString()
    )
}