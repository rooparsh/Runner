package com.darklabs.data.local.dao

import androidx.room.*
import com.darklabs.data.local.entity.Location
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Query("DELETE from location")
    suspend fun clearTable()

    @Query("SELECT * FROM location")
    fun getLocation(): Flow<Location>

    @Transaction
    suspend fun clearAndInsertLocation(location: Location) {
        clearTable()
        insertLocation(location)
    }

}