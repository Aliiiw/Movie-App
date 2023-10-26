package com.alirahimi.movieapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.alirahimi.movieapp.R
import com.alirahimi.movieapp.databinding.ActivityDetailBinding
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.utils.initRecycler
import com.alirahimi.movieapp.utils.showInvisible
import com.alirahimi.movieapp.viewmodel.DetailViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    private val viewModel: DetailViewModel by viewModels()

    private var movieId = 0

    @Inject
    lateinit var entity: MovieEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myJson = intent.getStringExtra("jsonObject")
        val gson = Gson()
        val item = gson.fromJson(myJson, MovieEntity::class.java)
        movieId = item.id
        viewModel.getMovieDetail(movieId)


        binding.apply {

            //load data
            viewModel.movieDetailLiveData.observe(this@DetailActivity) { movie ->
                posterBackgroundImage.load(movie.poster)
                posterBaseImage.load(movie.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameText.text = movie.title
                movieRateText.text = movie.imdbRating
                movieTimeText.text = movie.runtime
                movieRateText.text = movie.imdbRating
                movieDateText.text = movie.released
                movieSummaryInfo.text = movie.plot
                movieActorsInfo.text = movie.actors

                //adapter image
                imagesAdapter.differ.submitList(movie.images)

                imagesRecyclerView.initRecycler(
                    (LinearLayoutManager(
                        this@DetailActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )),
                    imagesAdapter
                )

                //favorite button
                favoriteImage.setOnClickListener {
                    entity.id = movieId
                    entity.poster = movie.poster
                    entity.title = movie.title
                    entity.rate = movie.rated
                    entity.year = movie.year
                    entity.country = movie.country

                    viewModel.favoriteMovieStatus(movieId, entity)

                }

            }

            //loading
            viewModel.loadingLiveData.observe(this@DetailActivity) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailScrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }

            lifecycleScope.launchWhenCreated {
                if (viewModel.existMovie(movieId)) {
                    favoriteImage.setColorFilter(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.scarlet
                        )
                    )
                } else {
                    favoriteImage.setColorFilter(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.philippineSilver
                        )
                    )
                }
            }

            //after click
            viewModel.isInFavorite.observe(this@DetailActivity) {
                if (it) {
                    favoriteImage.setColorFilter(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.scarlet
                        )
                    )
                } else {
                    favoriteImage.setColorFilter(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.philippineSilver
                        )
                    )
                }
            }

            //back button
            backImage.setOnClickListener {
                finish()
            }
        }
    }
}