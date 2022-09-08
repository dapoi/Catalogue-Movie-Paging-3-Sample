package com.dapascript.catmov.data

import androidx.paging.PagingData
import com.dapascript.catmov.data.remote.model.TopMoviesItem
import kotlinx.coroutines.flow.Flow

interface TopMoviesRepository {
    fun getTopMovies(): Flow<PagingData<TopMoviesItem>>
}