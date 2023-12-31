package com.alirahimi.movieapp.repository

import com.alirahimi.movieapp.db.MovieDao
import com.alirahimi.movieapp.db.MovieEntity
import javax.inject.Inject

class FavoriteRepository(private val dao: MovieDao) {

    suspend fun allFavoriteMovies() = dao.getAllMovies()
}