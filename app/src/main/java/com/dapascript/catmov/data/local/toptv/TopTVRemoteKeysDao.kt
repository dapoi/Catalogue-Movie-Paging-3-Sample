package com.dapascript.catmov.data.local.toptv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopTVRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<TopTVRemoteKeys>)

    @Query("SELECT * FROM top_tv_remote_keys WHERE tvId = :tvId")
    suspend fun remoteKeysTVId(tvId: Int): TopTVRemoteKeys?

    @Query("DELETE FROM top_tv_remote_keys")
    suspend fun deleteRemoteKeys()
}