package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.models.home.GenresListResponse
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import com.alirahimi.movieapp.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val topMoviesLiveData = MutableLiveData<MoviesTopListResponse>()
    val genresListLiveData = MutableLiveData<GenresListResponse>()
    val moviesLastListLiveData = MutableLiveData<MoviesTopListResponse>()

    val loadingLiveData = MutableLiveData<Boolean>()

    fun getTopMovies(id: Int) {
        viewModelScope.launch {
            val response = repository.getTopMovies(id)
            if (response.isSuccessful) {
                topMoviesLiveData.postValue(response.body())
            }
        }
    }

    fun getAllGenres() {
        viewModelScope.launch {
            val response = repository.getAllGenres()
            if (response.isSuccessful) {
                genresListLiveData.postValue(response.body())
            }
        }
    }

    fun getLastMovies() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val response = repository.getLastMovies()
            if (response.isSuccessful) {
                moviesLastListLiveData.postValue(response.body())
            }
        }
        loadingLiveData.postValue(false)
    }

}