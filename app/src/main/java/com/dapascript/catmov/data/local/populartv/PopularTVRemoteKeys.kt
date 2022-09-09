package com.dapascript.catmov.data.local.populartv

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_tv_remote_keys")
data class PopularTVRemoteKeys(
    @PrimaryKey val tvId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)