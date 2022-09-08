package com.dapascript.catmov.presentation.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dapascript.catmov.R
import com.dapascript.catmov.databinding.FragmentMoviesBinding
import com.dapascript.catmov.presentation.adapter.viewpager.MoviesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = MoviesPagerAdapter(activity as AppCompatActivity)
        binding.viewPager2.apply {
            adapter = pagerAdapter

            TabLayoutMediator(binding.tabsMovies, this) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.top_rated,
            R.string.now_playing
        )
    }
}