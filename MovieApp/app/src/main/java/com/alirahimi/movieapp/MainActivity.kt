package com.alirahimi.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.alirahimi.movieapp.databinding.ActivityMainBinding
import com.alirahimi.movieapp.ui.favorite.FavoriteFragment
import com.alirahimi.movieapp.ui.home.HomeFragment
import com.alirahimi.movieapp.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val favoriteFragment = FavoriteFragment()

        setCurrentFragment(homeFragment)


        binding.apply {
            bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> setCurrentFragment(homeFragment)
                    R.id.search -> setCurrentFragment(searchFragment)
                    R.id.favorite -> setCurrentFragment(favoriteFragment)
                }
                true
            }
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}