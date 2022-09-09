package com.dapascript.catmov.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dapascript.catmov.data.local.MediaDatabase
import com.dapascript.catmov.data.local.populartv.PopularTVRemoteKeys
import com.dapascript.catmov.data.remote.model.PopularTVItem
import com.dapascript.catmov.data.remote.network.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PopularTVMediator(
    private val apiService: ApiService,
    private val mediaDB: MediaDatabase
) : RemoteMediator<Int, PopularTVItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularTVItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if TopTVRemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getPopularTvShows(page)
            val popular = responseData.results
            val endOfPaginationReached = popular.isEmpty()

            mediaDB.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    mediaDB.popularTVRemoteKeysDao().deleteRemoteKeys()
                    mediaDB.popularTVDao().deletePopularTV()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = popular.map {
                    PopularTVRemoteKeys(tvId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                mediaDB.popularTVRemoteKeysDao().insertAllKeys(keys)
                mediaDB.popularTVDao().insertPopularTV(popular)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularTVItem>): PopularTVRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            mediaDB.popularTVRemoteKeysDao().remoteKeysPopularTVId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularTVItem>): PopularTVRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            mediaDB.popularTVRemoteKeysDao().remoteKeysPopularTVId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularTVItem>): PopularTVRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaDB.popularTVRemoteKeysDao().remoteKeysPopularTVId(id)
            }
        }
    }
}