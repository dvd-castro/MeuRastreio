package br.com.davidcastro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.davidcastro.data.db.entity.TrackingEntity

@Dao
interface TrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trackingEntity: TrackingEntity)

    @Query("SELECT * FROM rastreio")
    fun getAll(): List<TrackingEntity>

    @Query("SELECT * FROM rastreio WHERE codigo = (:code)")
    fun get(code: String): TrackingEntity

    @Query("DELETE FROM rastreio WHERE codigo = (:code)")
    fun delete(code: String)

    @Query("SELECT (SELECT COUNT(*) FROM rastreio WHERE codigo = (:code)) > 0")
    fun contains(code: String) : Boolean
}