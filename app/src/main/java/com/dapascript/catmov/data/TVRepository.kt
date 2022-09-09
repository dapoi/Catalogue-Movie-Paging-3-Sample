package com.dapascript.catmov.data

import androidx.paging.PagingData
import com.dapascript.catmov.data.remote.model.PopularTVItem
import com.dapascript.catmov.data.remote.model.TopTVItem
import kotlinx.coroutines.flow.Flow

interface TVRepository {

    // Top Rated TV Show
    fun getTopRatedTV(): Flow<PagingData<TopTVItem>>

    // Popular TV Show
    fun getPopularTVShow(): Flow<PagingData<PopularTVItem>>
}