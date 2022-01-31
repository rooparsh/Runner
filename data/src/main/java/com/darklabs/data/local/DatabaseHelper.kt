package com.darklabs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darklabs.data.local.conveter.BitmapConverter
import com.darklabs.data.local.dao.RunDao
import com.darklabs.data.local.entity.Run

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
object DatabaseHelper {

    private const val DATABASE_NAME = "database_name"

    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, RunningDatabase::class.java, DATABASE_NAME)
            .build()


    @Database(
        entities = [Run::class],
        version = 1
    )
    @TypeConverters(value = [BitmapConverter::class])
    abstract class RunningDatabase : RoomDatabase() {

        abstract fun getRunDao(): RunDao
    }
}