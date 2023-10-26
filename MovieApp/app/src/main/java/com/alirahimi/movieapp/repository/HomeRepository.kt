package com.alirahimi.movieapp.repository

import com.alirahimi.movieapp.api.ApiService
import javax.inject.Inject


class HomeRepository @Inject constructor(private val api: ApiService) {

    suspend fun getTopMovies(id: Int) = api.getTopMovies(id)

    suspend fun getAllGenres() = api.getGenresList()

    suspend fun getLastMovies() = api.getLastMovies()

}