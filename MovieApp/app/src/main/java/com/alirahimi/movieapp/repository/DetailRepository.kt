package com.alirahimi.movieapp.repository


import com.alirahimi.movieapp.api.ApiService
import com.alirahimi.movieapp.db.MovieDao
import com.alirahimi.movieapp.db.MovieEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiService, private val dao: MovieDao) {

    //api
    suspend fun getMovieDetail(id: Int) = api.getMovieDetail(id)


    //database
    suspend fun insertFavoriteMovie(entity: MovieEntity) = dao.insertMovie(entity)
    suspend fun deleteFavoriteMovie(entity: MovieEntity) = dao.deleteMovie(entity)
    suspend fun existFavoriteMovie(id: Int) = dao.existMovie(id)

}