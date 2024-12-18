package com.example.dicodingevent.ui.finishedevent

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

class FinishedEventViewModel : ViewModel() {
    private val _finishedEventList = MutableLiveData<List<ListEventsItem>?>()
    val finishedEventList: MutableLiveData<List<ListEventsItem>?> = _finishedEventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "FinishedEventViewModel"
        private const val ID = 0
    }

    init {
        fetchEvents()
        finishedEventList.observeForever { eventList ->
            Log.d("FinishedEventViewModel", "Data loaded in ViewModel: ${eventList?.size}")
        }
    }

    private fun fetchEvents() {
        _isLoading.value = true
        Log.d(TAG, "Fetching events...")
        val client = ApiConfig.getApiService().getEvents(ID)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val events = response.body()?.listEvents
                    _finishedEventList.value = events
                    Log.d(TAG, "Fetched events: ${events?.size}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
