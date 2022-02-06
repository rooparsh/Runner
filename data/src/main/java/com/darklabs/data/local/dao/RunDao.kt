package com.darklabs.data.local.dao

import androidx.room.*
import com.darklabs.data.local.entity.Run
import com.darklabs.data.local.entity.RunWithLocation
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rooparsh Kalia on 31/01/22
 */
@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run): Long

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate(): Flow<List<Run>>

    @Query("SELECT * FROM run ORDER BY timeInMillis DESC")
    fun getAllRunsSortedByTimeInMillis(): Flow<List<Run>>

    @Query("SELECT * FROM run ORDER BY caloriesBurned DESC")
    fun getAllRunsSortedByCaloriesBurned(): Flow<List<Run>>

    @Query("SELECT * FROM run ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeed(): Flow<List<Run>>

    @Query("SELECT * FROM run ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistance(): Flow<List<Run>>

    @Query("SELECT SUM(timeInMillis) FROM run")
    fun getTotalTimeInMillis(): Flow<Long>

    @Query("SELECT SUM(caloriesBurned) FROM run")
    fun getTotalCaloriesBurned(): Flow<Int>

    @Query("SELECT SUM(distanceInMeters) FROM run")
    fun getTotalDistance(): Flow<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM run")
    fun getTotalAvgSpeed(): Flow<Float>

    @Query("SELECT * FROM run WHERE isRunning = 1 LIMIT 1")
    suspend fun getOngoingRun(): Run?

    @Query("SELECT MAX(id) FROM run")
    suspend fun getPreviousRunId(): Long

    @Query("UPDATE run SET isRunning = 0")
    suspend fun stopPreviousRuns()

    @Update
    suspend fun updateRun(run: Run)

    @Transaction
    suspend fun createNewRun(run: Run): Long {
        stopPreviousRuns()
        return insertRun(run.copy(isRunning = true))
    }

    @Transaction
    @Query("SELECT * FROM run WHERE isRunning = 1 LIMIT 1")
    fun getOnGoingRunWithLocation(): Flow<RunWithLocation?>
}