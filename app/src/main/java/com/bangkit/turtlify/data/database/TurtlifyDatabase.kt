package com.bangkit.turtlify.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.turtlify.data.database.dao.TurtlifyDao
import com.bangkit.turtlify.data.database.entity.Turtle

@Database(entities = [Turtle::class], version = 1, exportSchema = false)
abstract class TurtlifyDatabase : RoomDatabase() {
    abstract fun turtlifyDao(): TurtlifyDao

    companion object {
        @Volatile
        private var INSTANCE: TurtlifyDatabase? = null

        fun getDatabase(context: Context): TurtlifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TurtlifyDatabase::class.java,
                    "turtlify_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
