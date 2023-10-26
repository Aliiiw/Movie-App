package com.alirahimi.movieapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.alirahimi.movieapp.MainActivity
import com.alirahimi.movieapp.databinding.ActivityRegisterBinding
import com.alirahimi.movieapp.models.register.RegisterPostBody
import com.alirahimi.movieapp.utils.StoreUserData
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.HomeViewModel
import com.alirahimi.movieapp.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    //init
    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var dataStore: StoreUserData

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var registerPostBody: RegisterPostBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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