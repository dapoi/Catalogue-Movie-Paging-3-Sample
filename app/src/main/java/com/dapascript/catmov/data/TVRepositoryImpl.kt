package com.dapascript.catmov.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dapascript.catmov.data.local.MediaDatabase
import com.dapascript.catmov.data.mediator.PopularTVMediator
import com.dapascript.catmov.data.mediator.TopTVMediator
import com.dapascript.catmov.data.remote.model.PopularTVItem
import com.dapascript.catmov.data.remote.model.TopTVItem
import com.dapascript.catmov.data.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mediaDB: MediaDatabase
) : TVRepository {

    override fun getTopRatedTV(): Flow<PagingData<TopTVItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = TopTVMediator(apiService, mediaDB),
            pagingSourceFactory = { mediaDB.topTVDao().getTopTV() }
        ).flow
    }

    override fun getPopularTVShow(): Flow<PagingData<PopularTVItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = PopularTVMediator(apiService, mediaDB),
            pagingSourceFactory = { mediaDB.popularTVDao().getPopularTV() }
        ).flow
    }
}