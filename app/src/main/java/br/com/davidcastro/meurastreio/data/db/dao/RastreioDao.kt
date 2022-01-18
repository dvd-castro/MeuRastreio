package br.com.davidcastro.meurastreio.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.davidcastro.meurastreio.data.db.entity.RastreioEntity

@Dao
interface RastreioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rastreioEntity: RastreioEntity)

    @Query("SELECT * FROM rastreio")
    fun getAll(): MutableList<RastreioEntity>

    @Query("SELECT * FROM rastreio WHERE codigo Like (:code)")
    fun get(code: String): RastreioEntity

    @Query("DELETE FROM rastreio WHERE codigo Like (:code)")
    fun delete(code: String)

    @Query("SELECT (SELECT COUNT(*) FROM rastreio WHERE codigo Like (:code)) > 0")
    fun contains(code: String) : Boolean
}