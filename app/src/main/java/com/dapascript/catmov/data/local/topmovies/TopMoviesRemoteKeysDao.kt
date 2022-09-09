package com.dapascript.catmov.data.local.topmovies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopMoviesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<TopMoviesRemoteKeys>)

    @Query("SELECT * FROM top_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysMovieId(movieId: Int): TopMoviesRemoteKeys?

    @Query("DELETE FROM top_remote_keys")
    suspend fun deleteRemoteKeys()
}