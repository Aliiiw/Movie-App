package com.alirahimi.movieapp.viewmodel

import android.graphics.Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) :
    ViewModel() {

    val favoriteMoviesListLiveData = MutableLiveData<MutableList<MovieEntity>>()
    val isEmptyListLiveData = MutableLiveData<Boolean>()

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            val list = repository.allFavoriteMovies()
            if (list.isNotEmpty()) {
                favoriteMoviesListLiveData.postValue(list)
                isEmptyListLiveData.postValue(false)
            } else {
                isEmptyListLiveData.postValue(true)
            }
        }
    }
}