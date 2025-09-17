package com.omkero.asicscanner.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Contact::class],
    version = 1
)
abstract class RoomDatabase: RoomDatabase() {

    abstract fun dao(): DatabaseDao

    companion object {
        @Volatile private var INSTANCE: RoomDatabase? = null

        // Singleton ContactDatabase with @Volatile INSTANCE + synchronized → ensures only one DB instance exists in the whole app.
        fun getDatabase(context: Context): RoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "global_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}