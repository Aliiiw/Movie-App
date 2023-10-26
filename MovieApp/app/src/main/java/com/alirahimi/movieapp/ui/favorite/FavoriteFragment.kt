package com.alirahimi.movieapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirahimi.movieapp.R
import com.alirahimi.movieapp.databinding.FragmentFavoriteBinding
import com.alirahimi.movieapp.utils.initRecycler
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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