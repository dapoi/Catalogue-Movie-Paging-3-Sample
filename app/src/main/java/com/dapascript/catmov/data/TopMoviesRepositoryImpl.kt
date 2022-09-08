package com.dapascript.catmov.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dapascript.catmov.data.local.TopMoviesDatabase
import com.dapascript.catmov.data.remote.model.TopMoviesItem
import com.dapascript.catmov.data.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopMoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val topMoviesDB: TopMoviesDatabase
) : TopMoviesRepository {

    override fun getTopMovies(): Flow<PagingData<TopMoviesItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = TopMoviesMediator(apiService, topMoviesDB),
            pagingSourceFactory = { topMoviesDB.topMoviesDao().getTopMovies() }
        ).flow
    }
}