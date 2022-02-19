package com.darklabs.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darklabs.data.local.conveter.BitmapConverter
import com.darklabs.data.local.dao.LocationDao
import com.darklabs.data.local.dao.RunDao
import com.darklabs.data.local.entity.Location
import com.darklabs.data.local.entity.Run

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
object DatabaseHelper {

    @Volatile
    private var INSTANCE: RunningDatabase? = null

    private const val DATABASE_NAME = "database_name"

    fun provideDatabase(context: Context): RunningDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(context, RunningDatabase::class.java, DATABASE_NAME)
                .build()
            INSTANCE = instance
            instance
        }
    }


    @Database(
        entities = [Run::class, Location::class],
        version = 1
    )
    @TypeConverters(value = [BitmapConverter::class])
    abstract class RunningDatabase : RoomDatabase() {

        abstract fun getRunDao(): RunDao
        abstract fun getLocationDao(): LocationDao
    }
}