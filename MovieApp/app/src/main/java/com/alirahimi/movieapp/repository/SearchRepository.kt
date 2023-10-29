package com.alirahimi.movieapp.repository

import com.alirahimi.movieapp.api.ApiService
import javax.inject.Inject

class SearchRepository(private val api: ApiService) {

    suspend fun searchMovie(name: String) = api.searchMovie(name)

}