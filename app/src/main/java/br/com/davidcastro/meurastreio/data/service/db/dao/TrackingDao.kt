package br.com.davidcastro.meurastreio.data.service.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.davidcastro.meurastreio.data.service.db.entity.TrackingEntity

@Dao
interface TrackingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackingEntity: TrackingEntity)

    @Query("SELECT * FROM rastreio")
    suspend fun getAll(): List<TrackingEntity>

    @Query("SELECT * FROM rastreio WHERE codigo = (:code)")
    suspend fun get(code: String): TrackingEntity

    @Query("DELETE FROM rastreio WHERE codigo = (:code)")
    suspend fun delete(code: String)

    @Query("SELECT (SELECT COUNT(*) FROM rastreio WHERE codigo = (:code)) > 0")
    suspend fun contains(code: String) : Boolean
}