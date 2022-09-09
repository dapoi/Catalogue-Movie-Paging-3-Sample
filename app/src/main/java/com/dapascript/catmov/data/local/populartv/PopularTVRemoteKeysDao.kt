package com.dapascript.catmov.data.local.populartv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PopularTVRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<PopularTVRemoteKeys>)

    @Query("SELECT * FROM popular_tv_remote_keys WHERE tvId = :tvId")
    suspend fun remoteKeysPopularTVId(tvId: Int): PopularTVRemoteKeys?

    @Query("DELETE FROM popular_tv_remote_keys")
    suspend fun deleteRemoteKeys()
}