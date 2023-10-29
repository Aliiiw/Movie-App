package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.models.home.GenresListResponse
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import com.alirahimi.movieapp.repository.HomeRepository
import kotlinx.coroutines.launch


class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("wrong dependency")
    }

}


class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

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