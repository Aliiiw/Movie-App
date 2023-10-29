package com.alirahimi.movieapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirahimi.movieapp.R
import com.alirahimi.movieapp.databinding.FragmentFavoriteBinding
import com.alirahimi.movieapp.utils.MyApp
import com.alirahimi.movieapp.utils.initRecycler
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.FavoriteViewModel
import com.alirahimi.movieapp.viewmodel.FavoriteViewModelFactory
import com.alirahimi.movieapp.viewmodel.HomeViewModel
import com.alirahimi.movieapp.viewmodel.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(MyApp.appModule.favoriteRepository)
        ).get(
            FavoriteViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter()

        binding.apply {
            //show all favorites
            viewModel.loadFavoriteMovies()

            //set list
            viewModel.favoriteMoviesListLiveData.observe(viewLifecycleOwner) {
                favoriteAdapter.setData(it)
                movieRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    favoriteAdapter
                )
            }

            //empty
            viewModel.isEmptyListLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    constraintEmptyList.showInvisible(true)
                    movieRecycler.showInvisible(false)
                } else {
                    constraintEmptyList.showInvisible(false)
                    movieRecycler.showInvisible(true)
                }
            }
        }
    }
}