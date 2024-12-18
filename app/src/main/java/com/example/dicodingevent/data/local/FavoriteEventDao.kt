package com.example.dicodingevent.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: FavoriteEventEntity)

    @Update
    suspend fun update(event: FavoriteEventEntity)

    @Delete
    suspend fun delete(event: FavoriteEventEntity)

    @Query("SELECT * FROM favorite_event ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<FavoriteEventEntity>>

    @Query("SELECT * FROM favorite_event WHERE eventId = :eventId LIMIT 1")
    suspend fun getFavoriteById(eventId: Int): FavoriteEventEntity?

    @Query("DELETE FROM favorite_event WHERE eventId = :eventId")
    suspend fun deleteById(eventId: Int)
}