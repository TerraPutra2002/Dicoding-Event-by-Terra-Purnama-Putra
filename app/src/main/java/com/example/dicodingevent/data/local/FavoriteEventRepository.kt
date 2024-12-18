package com.example.dicodingevent.data.local

import android.app.Application
import androidx.lifecycle.LiveData


class FavoriteEventRepository(application: Application) {
    private val favEventsDao: FavoriteEventDao
    init {
        val db = FavoriteEventDatabase.getDatabase(application)
        favEventsDao = db.favoriteEventDao()
    }
    fun getAllFavorites(): LiveData<List<FavoriteEventEntity>> = favEventsDao.getAllFavorites()

    suspend fun insert(eventEntity: FavoriteEventEntity) {
        favEventsDao.insert(eventEntity)
    }

    suspend fun getFavoriteById(id: Int): FavoriteEventEntity? {
        return favEventsDao.getFavoriteById(id)
    }

    suspend fun deleteById(id: Int) {
        favEventsDao.deleteById(id)
    }

    suspend fun delete(eventEntity: FavoriteEventEntity){
        favEventsDao.delete(eventEntity)
    }

    suspend fun update(eventEntity: FavoriteEventEntity){
        favEventsDao.update(eventEntity)
    }
}