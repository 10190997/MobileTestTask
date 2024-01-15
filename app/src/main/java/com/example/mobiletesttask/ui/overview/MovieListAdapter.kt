package com.example.mobiletesttask.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mobiletesttask.R
import com.example.mobiletesttask.databinding.ListItemBinding
import com.example.mobiletesttask.network.Movie

class MovieListAdapter(private val onItemClicked: (Movie) -> Unit) : ListAdapter<Movie,
        MovieListAdapter.MovieViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class MovieViewHolder(private var binding:
                          ListItemBinding
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val imgUrl = movie.poster.previewUrl
            imgUrl.let {
                val uri = imgUrl.toUri().buildUpon().scheme("https").build()
                binding.previewImage.load(uri){
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
            binding.tvDetails.text = binding.root.resources.getString(R.string.movie_details, movie.genres[0], movie.year.toString())
            binding.tvTitle.text = movie.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(movie)
        }
        holder.bind(movie)
    }
}