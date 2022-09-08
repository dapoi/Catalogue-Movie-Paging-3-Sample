package com.dapascript.catmov.presentation.ui.movie.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dapascript.catmov.data.TopMoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopMoviesViewModel @Inject
constructor(
    private val repository: TopMoviesRepositoryImpl
) : ViewModel() {
    fun getTopMovies() = repository.getTopMovies().cachedIn(viewModelScope)
}