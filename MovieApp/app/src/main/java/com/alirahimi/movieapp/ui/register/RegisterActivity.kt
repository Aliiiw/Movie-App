package com.alirahimi.movieapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.alirahimi.movieapp.MainActivity
import com.alirahimi.movieapp.databinding.ActivityRegisterBinding
import com.alirahimi.movieapp.di.AppModuleImplementation
import com.alirahimi.movieapp.models.register.RegisterPostBody
import com.alirahimi.movieapp.repository.RegisterRepository
import com.alirahimi.movieapp.utils.MyApp
import com.alirahimi.movieapp.utils.StoreUserData
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    //init
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dataStore: StoreUserData
    private lateinit var registerPostBody: RegisterPostBody
    private lateinit var registerRepository: RegisterRepository
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appModule = AppModuleImplementation(this)
        registerPostBody = appModule.userRegisterBody
        registerRepository = appModule.registerRepository

//        val viewModelFactory = viewModelFactory { RegisterViewModel(registerRepository) }
//        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        viewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(MyApp.appModule.registerRepository)
        ).get(RegisterViewModel::class.java)


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnRegister.setOnClickListener {

                val email = emailEditText.text.toString()
                val name = nameEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (name.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {

                    registerPostBody.email = email
                    registerPostBody.name = name
                    registerPostBody.password = password

                    //view model
                    viewModel.userRegister(registerPostBody)

                    viewModel.userRegisterLoadingLiveData.observe(this@RegisterActivity) { loading ->
                        if (loading) {
                            progressBarLoading.showInvisible(true)
                            btnRegister.showInvisible(false)
                        } else {
                            progressBarLoading.showInvisible(false)
                            btnRegister.showInvisible(true)
                        }
                    }

                    viewModel.userRegisterLiveData.observe(this@RegisterActivity) { response ->
                        lifecycle.coroutineScope.launchWhenCreated {
                            dataStore.saveUserToken(response.name)
                        }
                    }

                    Intent(this@RegisterActivity, MainActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Snackbar.make(it, "مقادیر نمی توانند خالی باشند", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}


//    @Inject
//    lateinit var dataStore: StoreUserData

//private val viewModel: RegisterViewModel by viewModels()

//    private val viewModel = viewModelFactory {
//        RegisterViewModel()
//    }


//    @Inject
//    lateinit var registerPostBody: RegisterPostBody