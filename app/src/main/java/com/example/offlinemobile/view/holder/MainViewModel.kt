package com.example.offlinemobile.view.holder

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.offlinemobile.api.ApiResponseStatus
import com.example.offlinemobile.repository.MainRepository
import com.example.offlinemobile.room.News
import com.example.offlinemobile.room.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application):AndroidViewModel(application) {

    private val database = getDatabase(application)
    private  val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    val newsList = repository.newsList

    init {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                repository.fetchNews()
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.ERROR
                Log.d("LOGCATT", "No internet conection")
            }
        }

    }

    fun deleteAllItems() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteItem(news: News) = viewModelScope.launch {
        repository.deleteItemNew(news)
    }

    fun refreshNews () = viewModelScope.launch{
        try {
            _status.value = ApiResponseStatus.LOADING
            repository.fetchNews()
            _status.value = ApiResponseStatus.DONE
        } catch (e: UnknownHostException) {
            _status.value = ApiResponseStatus.ERROR
            Log.d("LOGCATT", "No internet conection")
        }
    }
}