package com.alirahimi.movieapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alirahimi.movieapp.databinding.ItemHomeTopMoviesBinding
import com.alirahimi.movieapp.models.home.MoviesTopListResponse.Data
import javax.inject.Inject

class TopMoviesAdapter : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeTopMoviesBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMoviesAdapter.ViewHolder {
        binding =
            ItemHomeTopMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: TopMoviesAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = if (differ.currentList.size > 5) 5 else differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Data) {
            binding.apply {
                movieTextName.text = item.title
                movieTextInfo.text = "${item.imdbRating} | ${item.country} | ${item.year}"
                moviePosterImage.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
            }
        }
    }

    private val differCalBack = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCalBack)
}