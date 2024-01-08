package com.example.mobiletesttask.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletesttask.network.KinopoiskApi
import com.example.mobiletesttask.network.Movie
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    fun getMoviesList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _movies.value = KinopoiskApi.retrofitService.getAllMovies().movies
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _movies.value = listOf()
                _status.value = ApiStatus.ERROR
            }
        }
    }
}