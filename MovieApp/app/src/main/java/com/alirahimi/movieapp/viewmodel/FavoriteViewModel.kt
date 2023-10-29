package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModelFactory(private val repository: FavoriteRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("wrong dependency")
    }

}


class FavoriteViewModel(private val repository: FavoriteRepository) :
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