package com.alirahimi.movieapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirahimi.movieapp.databinding.ItemHomeMoviesLastBinding
import com.alirahimi.movieapp.db.MovieEntity
import com.alirahimi.movieapp.models.home.MoviesTopListResponse
import javax.inject.Inject

class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeMoviesLastBinding
    private var lastMovieList = emptyList<MovieEntity>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.ViewHolder {
        binding =
            ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bindItems(lastMovieList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = lastMovieList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MovieEntity) {
            binding.apply {
                moviePosterImage.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameText.text = item.title
                movieRate.text = item.rate
                movieCountry.text = item.country
                movieYear.text = item.year

                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((MovieEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieEntity) -> Unit) {
        onItemClickListener = listener
    }


    fun setData(items: List<MovieEntity>) {
        val moviesDiffUtil = MoviesDiffUtils(lastMovieList, items)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        lastMovieList = items
        diffUtils.dispatchUpdatesTo(this)
    }

    //check the list better way
    class MoviesDiffUtils(
        private val oldItem: List<MovieEntity>,
        private val newItem: List<MovieEntity>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}