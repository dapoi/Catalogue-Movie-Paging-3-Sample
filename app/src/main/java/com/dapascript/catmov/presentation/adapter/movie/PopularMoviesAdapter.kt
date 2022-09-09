package com.dapascript.catmov.presentation.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dapascript.catmov.data.remote.model.PopularMoviesItem
import com.dapascript.catmov.databinding.ItemMoviesBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class PopularMoviesAdapter :
    PagingDataAdapter<PopularMoviesItem, PopularMoviesAdapter.NowPlayingViewHolder>(DIFF_CALLBACK) {

    inner class NowPlayingViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PopularMoviesItem) {
            binding.apply {
                tvMovieTitle.text = data.title
                tvOverview.text = data.overview
                tvVoteAverage.text = data.vote_average.toString()

                val imageShimmer =
                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                        .setBaseAlpha(0.7f) //the alpha of the underlying children
                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                        .setAutoStart(true)
                        .build()

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${data.poster_path}")
                    .placeholder(ShimmerDrawable().apply { setShimmer(imageShimmer) })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivMoviePoster)
            }
        }
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        return NowPlayingViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMoviesItem>() {
            override fun areItemsTheSame(
                oldItem: PopularMoviesItem,
                newItem: PopularMoviesItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularMoviesItem,
                newItem: PopularMoviesItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}