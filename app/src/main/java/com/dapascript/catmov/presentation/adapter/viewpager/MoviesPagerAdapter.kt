package com.dapascript.catmov.presentation.adapter.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dapascript.catmov.presentation.ui.movie.child.NowPlayingFragment
import com.dapascript.catmov.presentation.ui.movie.child.TopMoviesFragment

class MoviesPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TopMoviesFragment()
            1 -> NowPlayingFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}