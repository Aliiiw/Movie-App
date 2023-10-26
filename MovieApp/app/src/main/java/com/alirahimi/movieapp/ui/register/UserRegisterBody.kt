package com.alirahimi.movieapp.ui.register

import com.alirahimi.movieapp.models.register.RegisterPostBody
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRegisterBody {

    @Provides
    @Singleton
    fun provideUserRegisterBody() = RegisterPostBody()
}