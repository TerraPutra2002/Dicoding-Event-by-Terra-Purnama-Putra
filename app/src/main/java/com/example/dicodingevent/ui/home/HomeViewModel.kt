package com.example.dicodingevent.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _upcomingEventList = MutableLiveData<List<ListEventsItem>>()
    val upcomingEventList: LiveData<List<ListEventsItem>> = _upcomingEventList

    private val _finishedEventList = MutableLiveData<List<ListEventsItem>>()
    val finishedEventList: LiveData<List<ListEventsItem>> = _finishedEventList

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    companion object {
        private const val TAG = "HomeViewModel"
        private const val UPCOMING_EVENTS_ID = 1
        private const val FINISHED_EVENTS_ID = 0
    }

    init {
        fetchUpcomingEvents()
        fetchFinishedEvents()
    }

    private fun fetchUpcomingEvents() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getEvents(UPCOMING_EVENTS_ID)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isloading.value = false
                if (response.isSuccessful) {
                    _upcomingEventList.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun fetchFinishedEvents() {
        _isloading.value = true
        val client = ApiConfig.getApiService().getEvents(FINISHED_EVENTS_ID)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isloading.value = false
                if (response.isSuccessful) {
                    _finishedEventList.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}