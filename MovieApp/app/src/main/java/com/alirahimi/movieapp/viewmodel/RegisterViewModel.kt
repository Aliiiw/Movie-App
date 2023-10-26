package com.alirahimi.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alirahimi.movieapp.models.register.RegisterPostBody
import com.alirahimi.movieapp.models.register.RegisterResponse
import com.alirahimi.movieapp.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    ViewModel() {

    val userRegisterLiveData = MutableLiveData<RegisterResponse>()
    val userRegisterLoadingLiveData = MutableLiveData<Boolean>()

    fun userRegister(body: RegisterPostBody) {
        viewModelScope.launch {
            userRegisterLoadingLiveData.postValue(true)
            val response = repository.userRegister(body)
            if (response.isSuccessful) {
                userRegisterLiveData.postValue(response.body())
            }
            userRegisterLoadingLiveData.postValue(false)
        }
    }
}