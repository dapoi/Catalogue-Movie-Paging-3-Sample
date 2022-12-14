package com.dapascript.catmov.presentation.ui.movie.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dapascript.catmov.data.MoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopMoviesViewModel @Inject
constructor(
    private val repository: MoviesRepositoryImpl
) : ViewModel() {
    fun getTopMovies() = repository.getTopMovies().cachedIn(viewModelScope)
}