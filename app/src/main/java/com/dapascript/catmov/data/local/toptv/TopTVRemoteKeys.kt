package com.dapascript.catmov.data.local.toptv

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_tv_remote_keys")
data class TopTVRemoteKeys(
    @PrimaryKey val tvId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)