package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import com.alirahimi.movieapp.repository.HomeRepository
import com.alirahimi.movieapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModelFactory(private val repository: SearchRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("wrong dependency")
    }

}


class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

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