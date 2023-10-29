package com.alirahimi.movieapp.repository

import com.alirahimi.movieapp.api.ApiService
import com.alirahimi.movieapp.models.register.RegisterPostBody
import javax.inject.Inject

class RegisterRepository(private val api: ApiService) {

    suspend fun userRegister(body: RegisterPostBody) = api.userRegister(body)
}