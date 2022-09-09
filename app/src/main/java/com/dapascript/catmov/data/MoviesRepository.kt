package com.dapascript.catmov.data

import androidx.paging.PagingData
import com.dapascript.catmov.data.remote.model.PopularMoviesItem
import com.dapascript.catmov.data.remote.model.TopMoviesItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    // get top movies
    fun getTopMovies(): Flow<PagingData<TopMoviesItem>>

    // get now playing movies
    fun getPopularMovies(): Flow<PagingData<PopularMoviesItem>>
}