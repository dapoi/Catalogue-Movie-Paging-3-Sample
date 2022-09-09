package com.dapascript.catmov.data.local.popularmovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_remote_keys")
data class PopularMoviesRemoteKeys(
    @PrimaryKey val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)