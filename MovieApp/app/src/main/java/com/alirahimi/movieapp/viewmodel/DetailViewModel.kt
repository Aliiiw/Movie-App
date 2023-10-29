package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.models.detail.MovieDetailResponse
import com.alirahimi.movieapp.repository.DetailRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModelFactory(private val repository: DetailRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("wrong dependency")
    }

}


class DetailViewModel(private val repository: DetailRepository) :
    ViewModel() {

    val movieDetailLiveData = MutableLiveData<MovieDetailResponse>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            val response = repository.getMovieDetail(id)
            if (response.isSuccessful) {
                movieDetailLiveData.postValue(response.body())
            }
            loadingLiveData.postValue(false)
        }
    }

    //database
    val isInFavorite = MutableLiveData<Boolean>()

    suspend fun existMovie(id: Int) = withContext(viewModelScope.coroutineContext) {
        repository.existFavoriteMovie(id)
    }

    fun favoriteMovieStatus(id: Int, entity: MovieEntity) {
        viewModelScope.launch {
            val isExist = repository.existFavoriteMovie(id)

            if (isExist) {
                isInFavorite.postValue(false)
                repository.deleteFavoriteMovie(entity)
            } else {
                isInFavorite.postValue(true)
                repository.insertFavoriteMovie(entity)
            }
        }
    }
}