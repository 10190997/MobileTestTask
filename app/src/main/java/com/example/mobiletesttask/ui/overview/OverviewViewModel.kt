package com.example.mobiletesttask.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mobiletesttask.network.KinopoiskApi
import com.example.mobiletesttask.network.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    private lateinit var filteredList: List<Movie>

    fun getMoviesList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val received = KinopoiskApi.retrofitService.getAllMovies().movies
                _movies.value = received
                _status.value = ApiStatus.DONE

                filteredList = received
            } catch (e: Exception) {
                _movies.value = listOf()
                _status.value = ApiStatus.ERROR

                filteredList = listOf()
            }
        }
    }

    fun filter(query: String?) {
        viewModelScope.launch {
            delay(500)
            _movies.value = filteredList.filter {
                it.anyContains(query)
            }
        }
    }

    fun getMovie(id: Int): LiveData<Movie> {
        return liveData {
            emit(KinopoiskApi.retrofitService.getMovieById(id))
        }
    }
}