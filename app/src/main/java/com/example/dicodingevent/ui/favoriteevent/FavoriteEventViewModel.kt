package com.example.dicodingevent.ui.favoriteevent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.local.FavoriteEventEntity
import com.example.dicodingevent.data.local.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteEventRepository = FavoriteEventRepository(application)
    val favoriteList: LiveData<List<FavoriteEventEntity>> = favoriteEventRepository.getAllFavorites()

    init {
        fetchFavoriteEvents()
    }

    private fun fetchFavoriteEvents() {
        viewModelScope.launch {
            favoriteEventRepository.getAllFavorites()
        }
    }
}