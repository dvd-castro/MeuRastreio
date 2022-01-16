package br.com.davidcastro.meurastreio.data.db.dao

import androidx.room.*
import br.com.davidcastro.meurastreio.data.db.entity.RastreioEntity

@Dao
interface RastreioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rastreioEntity: RastreioEntity)

    @Query("SELECT * FROM rastreio")
    fun getAll(): MutableList<RastreioEntity>

    @Query("SELECT * FROM rastreio WHERE codigo Like (:code)")
    fun getRastreio(code: String): RastreioEntity

    @Query("DELETE FROM rastreio WHERE codigo Like (:code)")
    fun delete(code: String)
}