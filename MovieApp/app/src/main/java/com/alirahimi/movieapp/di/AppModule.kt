package com.alirahimi.movieapp.di

import android.content.Context
import androidx.room.Room
import com.alirahimi.movieapp.api.ApiService
import com.alirahimi.movieapp.db.MovieDao
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.db.MoviesDatabase
import com.alirahimi.movieapp.models.register.RegisterPostBody
import com.alirahimi.movieapp.repository.*
import com.alirahimi.movieapp.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface AppModule {
    val baseUrl: String
    val connectionTime: Long
    val gson: Gson
    val interceptor: HttpLoggingInterceptor
    val client: OkHttpClient
    val retrofit: ApiService
    val database: MoviesDatabase
    val dao: MovieDao
    val entity: MovieEntity
    val userRegisterBody: RegisterPostBody
    val homeRepository: HomeRepository
    val registerRepository: RegisterRepository
    val detailRepository: DetailRepository
    val searchRepository: SearchRepository
    val favoriteRepository: FavoriteRepository
}


class AppModuleImplementation(
    private val appContext: Context

) : AppModule {

    override val retrofit: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

    override val baseUrl: String by lazy {
        Constants.BASE_URL
    }

    override val connectionTime: Long by lazy {
        Constants.CONNECTION_TIME
    }

    override val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    override val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    override val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .writeTimeout(connectionTime, TimeUnit.SECONDS)
            .readTimeout(connectionTime, TimeUnit.SECONDS)
            .connectTimeout(connectionTime, TimeUnit.SECONDS)
            .build()
    }

    override val database: MoviesDatabase by lazy {
        Room.databaseBuilder(appContext, MoviesDatabase::class.java, Constants.MOVIES_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override val dao: MovieDao by lazy {
        database.movieDao()
    }

    override val entity: MovieEntity by lazy {
        MovieEntity()
    }

    override val userRegisterBody: RegisterPostBody by lazy {
        RegisterPostBody()
    }

    override val homeRepository: HomeRepository by lazy {
        HomeRepository(retrofit)
    }

    override val detailRepository: DetailRepository by lazy {
        DetailRepository(retrofit, dao)
    }

    override val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepository(dao)
    }
    override val searchRepository: SearchRepository by lazy {
        SearchRepository(retrofit)
    }
    override val registerRepository: RegisterRepository by lazy {
        RegisterRepository(retrofit)
    }
}
