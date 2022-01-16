package br.com.davidcastro.meurastreio.data.source

import android.content.Context
import br.com.davidcastro.meurastreio.data.db.AppDatabase
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.helpers.extensions.toRastreioEntity
import br.com.davidcastro.meurastreio.helpers.extensions.toRastreioModel

open class DatabaseDataSource(context: Context){

    private val dao = AppDatabase.getDatabase(context).rastreioDao

    fun inserirRastreio(rastreioModel: RastreioModel) {
        dao.insert(rastreioModel.toRastreioEntity())
    }

    fun getRastreio(codigo: String): RastreioModel {
        return dao.getRastreio(codigo).toRastreioModel()
    }

    fun deleteRastreio(codigo: String) {
        dao.delete(codigo)
    }
    fun getAll(): List<RastreioModel> {
        val data = dao.getAll()

        val list = arrayListOf<RastreioModel>()

        data.forEach {
            list.add(it.toRastreioModel())
        }

        return list
    }

}