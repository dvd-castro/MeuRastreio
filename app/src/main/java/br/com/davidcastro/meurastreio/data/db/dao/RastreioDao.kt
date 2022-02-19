package br.com.davidcastro.meurastreio.data.db.dao

import androidx.room.*
import br.com.davidcastro.meurastreio.data.db.entity.RastreioEntity

@Dao
interface RastreioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rastreioEntity: RastreioEntity)

    @Query("SELECT * FROM rastreio")
    fun getAll(): MutableList<RastreioEntity>

    @Query("SELECT * FROM rastreio WHERE codigo = (:code)")
    fun get(code: String): RastreioEntity

    @Query("DELETE FROM rastreio WHERE codigo = (:code)")
    fun delete(code: String)

    @Query("UPDATE rastreio SET eventos = (:events) WHERE codigo = (:code)")
    fun update(code: String, events: String)

    @Query("SELECT (SELECT COUNT(*) FROM rastreio WHERE codigo = (:code)) > 0")
    fun contains(code: String) : Boolean
}