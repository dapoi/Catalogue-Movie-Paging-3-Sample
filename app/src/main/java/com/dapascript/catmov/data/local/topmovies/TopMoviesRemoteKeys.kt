package com.dapascript.catmov.data.local.topmovies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class TopMoviesRemoteKeys(
    @PrimaryKey val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)