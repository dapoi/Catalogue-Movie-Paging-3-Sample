package com.dapascript.catmov.presentation.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dapascript.catmov.data.remote.model.TopTVItem
import com.dapascript.catmov.databinding.ItemTvShowsTopRatedBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class TopTVAdapter : PagingDataAdapter<TopTVItem, TopTVAdapter.TopTVViewHolder>(DIFF_CALLBACK) {

    inner class TopTVViewHolder(
        private val binding: ItemTvShowsTopRatedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topTVItem: TopTVItem) {
            binding.apply {
                tvTvTitle.text = topTVItem.name
                tvOverview.text = topTVItem.overview
                tvVoteAverage.text = topTVItem.vote_average.toString()

                val imageShimmer =
                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                        .setBaseAlpha(0.7f) //the alpha of the underlying children
                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                        .setAutoStart(true)
                        .build()

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${topTVItem.poster_path}")
                    .placeholder(ShimmerDrawable().apply { setShimmer(imageShimmer) })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivTvPoster)
            }
        }
    }

    override fun onBindViewHolder(holder: TopTVViewHolder, position: Int) {
        val topTVItem = getItem(position)
        if (topTVItem != null) {
            holder.bind(topTVItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTVViewHolder {
        return TopTVViewHolder(
            ItemTvShowsTopRatedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopTVItem>() {
            override fun areItemsTheSame(oldItem: TopTVItem, newItem: TopTVItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TopTVItem, newItem: TopTVItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}