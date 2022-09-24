package br.com.davidcastro.meurastreio.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.davidcastro.meurastreio.data.db.entity.TrackingEntity

@Dao
interface TrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trackingEntity: TrackingEntity)

    @Query("SELECT * FROM rastreio")
    fun getAll(): MutableList<TrackingEntity>

    @Query("SELECT * FROM rastreio WHERE code = (:code)")
    fun get(code: String): TrackingEntity

    @Query("DELETE FROM rastreio WHERE code = (:code)")
    fun delete(code: String)

    @Query("SELECT (SELECT COUNT(*) FROM rastreio WHERE code = (:code)) > 0")
    fun contains(code: String) : Boolean
}