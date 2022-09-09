package com.dapascript.catmov.data.local.popularmovies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PopularMoviesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<PopularMoviesRemoteKeys>)

    @Query("SELECT * FROM popular_movies_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysPopularMoviesId(movieId: Int): PopularMoviesRemoteKeys?

    @Query("DELETE FROM popular_movies_remote_keys")
    suspend fun deleteRemoteKeys()
}