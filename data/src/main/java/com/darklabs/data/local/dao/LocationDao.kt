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

    @Query("SELECT * FROM location WHERE runId = :runId ORDER BY id DESC LIMIT 1")
    fun getLatestLocation(runId: Long): Flow<Location>

    @Query("SELECT * FROM location")
    fun getAllLocations(): Flow<List<Location>>

    @Transaction
    suspend fun clearAndInsertLocation(location: Location) {
        clearTable()
        insertLocation(location)
    }

}