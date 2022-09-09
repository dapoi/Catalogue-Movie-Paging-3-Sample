package com.dapascript.catmov.presentation.ui.movie.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dapascript.catmov.data.MoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repositoryImpl: MoviesRepositoryImpl
) : ViewModel() {
    fun getNowPlayingMovies() = repositoryImpl.getPopularMovies().cachedIn(viewModelScope)
}