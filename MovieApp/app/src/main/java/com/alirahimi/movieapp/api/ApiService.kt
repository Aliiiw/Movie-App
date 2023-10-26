package com.alirahimi.movieapp.api

import com.alirahimi.movieapp.models.detail.MovieDetailResponse
import com.alirahimi.movieapp.models.home.GenresListResponse
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import com.alirahimi.movieapp.models.register.RegisterPostBody
import com.alirahimi.movieapp.models.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("v1/register")
    suspend fun userRegister(@Body body: RegisterPostBody): Response<RegisterResponse>

    @GET("v1/genres/{genre_id}/movies")
    suspend fun getTopMovies(@Path("genre_id") id: Int): Response<MoviesTopListResponse>

    @GET("v1/genres")
    suspend fun getGenresList(): Response<GenresListResponse>

    @GET("v1/movies")
    suspend fun getLastMovies(): Response<MoviesTopListResponse>

    @GET("v1/movies")
    suspend fun searchMovie(@Query("q") name: String): Response<MoviesTopListResponse>

    @GET("v1/movies/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id: Int): Response<MovieDetailResponse>

}