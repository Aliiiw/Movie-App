package com.alirahimi.movieapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.alirahimi.movieapp.R
import com.alirahimi.movieapp.databinding.FragmentHomeBinding
import com.alirahimi.movieapp.ui.detail.DetailActivity
import com.alirahimi.movieapp.ui.home.adapters.GenresAdapter
import com.alirahimi.movieapp.ui.home.adapters.LastMoviesAdapter
import com.alirahimi.movieapp.ui.home.adapters.TopMoviesAdapter
import com.alirahimi.movieapp.utils.initRecycler
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    private val viewModel: HomeViewModel by viewModels()

    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    @Inject
    lateinit var lastMoviesAdapter: LastMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //call api
        viewModel.getTopMovies(3)
        viewModel.getAllGenres()
        viewModel.getLastMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //get top movies
            viewModel.topMoviesLiveData.observe(viewLifecycleOwner) {
                topMoviesAdapter.differ.submitList(it.data)

                //recycler view
                homeTopMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    topMoviesAdapter
                )
                //indicator
                pagerHelper.attachToRecyclerView(homeTopMoviesRecycler)
                topMoviesIndicator.attachToRecyclerView(homeTopMoviesRecycler, pagerHelper)
            }

            //get the genres
            viewModel.genresListLiveData.observe(viewLifecycleOwner) {
                genresAdapter.differ.submitList(it)
                genresRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    genresAdapter
                )
            }

            //get last movies
            viewModel.moviesLastListLiveData.observe(viewLifecycleOwner) {
                lastMoviesAdapter.setData(it.data)
                lastMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
                    lastMoviesAdapter
                )
            }

            //click
            lastMoviesAdapter.setOnItemClickListener {
                val intent = Intent(activity, DetailActivity::class.java)
                val gson = Gson()
                val myJson: String = gson.toJson(it)
                intent.putExtra("jsonObject", myJson)
                startActivity(intent)
            }

            //get loading
            viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    moviesLoading.showInvisible(true)
                    homeScrollView.showInvisible(false)
                } else {
                    moviesLoading.showInvisible(false)
                    homeScrollView.showInvisible(true)
                }
            }
        }
    }
}