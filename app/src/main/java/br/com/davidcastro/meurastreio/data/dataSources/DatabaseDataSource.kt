package br.com.davidcastro.meurastreio.data.dataSources

import android.content.Context
import br.com.davidcastro.meurastreio.data.db.AppDatabase

open class DatabaseDataSource(context: Context) {

//    private val dao = AppDatabase.getDatabase(context).rastreioDao
//
//    fun insert(rastreio: RastreioModel) {
//        dao.insert(rastreio.toRastreioEntity())
//    }
//
//    fun get(codigo: String): RastreioModel {
//        return dao.get(codigo).toRastreioModel()
//    }
//
//    fun delete(codigo: String) {
//        dao.delete(codigo)
//    }
//
//    fun update(codigo: String, eventos: String) {
//        dao.update(codigo, eventos)
//    }
//
//    fun contains(codigo: String): Boolean {
//        return dao.contains(codigo)
//    }
//
//    fun getAll(): List<RastreioModel> {
//        val data = dao.getAll()
//        val list = arrayListOf<RastreioModel>()
//
//        data.forEach {
//            list.add(it.toRastreioModel())
//        }
//
//        return list
//    }
}