package com.alirahimi.movieapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirahimi.movieapp.R
import com.alirahimi.movieapp.databinding.FragmentSearchBinding
import com.alirahimi.movieapp.ui.home.adapters.LastMoviesAdapter
import com.alirahimi.movieapp.utils.initRecycler
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchAdapter: LastMoviesAdapter

    private val viewModel: SearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init
        binding.apply {
            searchEditText.addTextChangedListener {
                val movieName = it.toString()
                if (movieName.isNotEmpty()) {
                    viewModel.searchMovie(movieName)
                }
            }
            //get movie list
            viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
                searchAdapter.setData(it.data)
                searchMovieRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    searchAdapter
                )
            }

            //get loading
            viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    constraintLoadingProgressBar.showInvisible(true)
                } else {
                    constraintLoadingProgressBar.showInvisible(false)
                }
            }

            //get empty list
            viewModel.emptyMovieListStatusLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    constraintEmptyList.showInvisible(true)
                    searchMovieRecycler.showInvisible(false)
                } else {
                    constraintEmptyList.showInvisible(false)
                    searchMovieRecycler.showInvisible(true)
                }
            }
        }
    }
}