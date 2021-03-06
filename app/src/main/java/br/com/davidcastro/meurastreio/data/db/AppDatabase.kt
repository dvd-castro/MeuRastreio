package br.com.davidcastro.meurastreio.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.davidcastro.meurastreio.data.db.dao.RastreioDao
import br.com.davidcastro.meurastreio.data.db.entity.RastreioEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "appdatabase"

@Database(entities = [RastreioEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val rastreioDao: RastreioDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}