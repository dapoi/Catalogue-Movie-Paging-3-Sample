package com.dapascript.catmov.presentation.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dapascript.catmov.data.remote.model.PopularTVItem
import com.dapascript.catmov.databinding.ItemTvShowsPopularBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class PopularTVAdapter : PagingDataAdapter<PopularTVItem, PopularTVAdapter.PopularTVViewHolder>(
    DIFF_CALLBACK
) {

    inner class PopularTVViewHolder(
        private val binding: ItemTvShowsPopularBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularTVItem: PopularTVItem) {
            binding.apply {
                tvTvTitle.text = popularTVItem.name
                tvOverview.text = popularTVItem.overview
                tvVoteAverage.text = popularTVItem.vote_average.toString()

                val imageShimmer =
                    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                        .setBaseAlpha(0.7f) //the alpha of the underlying children
                        .setHighlightAlpha(0.6f) // the shimmer alpha amount
                        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                        .setAutoStart(true)
                        .build()

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${popularTVItem.poster_path}")
                    .placeholder(ShimmerDrawable().apply { setShimmer(imageShimmer) })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivTvPoster)
            }
        }
    }

    override fun onBindViewHolder(holder: PopularTVViewHolder, position: Int) {
        val popularTVItem = getItem(position)
        if (popularTVItem != null) {
            holder.bind(popularTVItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTVViewHolder {
        return PopularTVViewHolder(
            ItemTvShowsPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularTVItem>() {
            override fun areItemsTheSame(oldItem: PopularTVItem, newItem: PopularTVItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularTVItem,
                newItem: PopularTVItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}