package br.com.davidcastro.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.davidcastro.data.db.dao.TrackingDao
import br.com.davidcastro.data.db.entity.TrackingEntity
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration

private const val DATABASE_VERSION = 2
private const val DATABASE_NAME = "appdatabase"

@Database(entities = [TrackingEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val trackingDao: TrackingDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE rastreio ADD COLUMN hasUpdated INTEGER")
                db.execSQL("ALTER TABLE rastreio ADD COLUMN hasCompleted INTEGER")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}