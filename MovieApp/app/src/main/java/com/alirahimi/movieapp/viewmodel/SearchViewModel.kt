package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import com.alirahimi.movieapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val moviesListLiveData = MutableLiveData<MoviesTopListResponse>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val emptyMovieListStatusLiveData = MutableLiveData<Boolean>()

    fun searchMovie(name: String) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val response = repository.searchMovie(name)
            if (response.isSuccessful) {
                if (response.body()?.data!!.isNotEmpty()) {
                    emptyMovieListStatusLiveData.postValue(false)
                    moviesListLiveData.postValue(response.body())
                } else {
                    emptyMovieListStatusLiveData.postValue(true)
                }
            }
        }
        loadingLiveData.postValue(false)
    }


}