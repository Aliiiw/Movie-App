package com.alirahimi.movieapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alirahimi.movieapp.MainActivity
import com.alirahimi.movieapp.databinding.ActivitySplashBinding
import com.alirahimi.movieapp.ui.register.RegisterActivity
import com.alirahimi.movieapp.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var dataStore: StoreUserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set delay
        CoroutineScope(Dispatchers.IO).launch {
            delay(2500)

            //check user token
            dataStore.getUserToken().collect {
                if (it.isEmpty()) {
                    Intent(this@SplashActivity, RegisterActivity::class.java).also { goToRegister ->
                        startActivity(goToRegister)
                    }
                    finish()
                } else {
                    Intent(this@SplashActivity, MainActivity::class.java).also { goToMain ->
                        startActivity(goToMain)
                    }
                    finish()
                }
            }
        }
    }
}