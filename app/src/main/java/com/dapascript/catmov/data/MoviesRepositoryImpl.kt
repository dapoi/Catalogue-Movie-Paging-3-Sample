package com.dapascript.catmov.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dapascript.catmov.data.local.MediaDatabase
import com.dapascript.catmov.data.mediator.PopularMoviesMediator
import com.dapascript.catmov.data.mediator.TopMoviesMediator
import com.dapascript.catmov.data.remote.model.PopularMoviesItem
import com.dapascript.catmov.data.remote.model.TopMoviesItem
import com.dapascript.catmov.data.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesDB: MediaDatabase
) : MoviesRepository {

    override fun getTopMovies(): Flow<PagingData<TopMoviesItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = TopMoviesMediator(apiService, moviesDB),
            pagingSourceFactory = { moviesDB.topMoviesDao().getTopMovies() }
        ).flow
    }

    override fun getPopularMovies(): Flow<PagingData<PopularMoviesItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = PopularMoviesMediator(apiService, moviesDB),
            pagingSourceFactory = { moviesDB.popularMoviesDao().getPopularMovies() }
        ).flow
    }

}