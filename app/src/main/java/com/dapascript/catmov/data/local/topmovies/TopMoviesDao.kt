package com.dapascript.catmov.data.local.topmovies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dapascript.catmov.data.remote.model.TopMoviesItem

@Dao
interface TopMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopMovies(topMovies: List<TopMoviesItem>)

    @Query("SELECT * FROM top_movies")
    fun getTopMovies(): PagingSource<Int, TopMoviesItem>

    @Query("DELETE FROM top_movies")
    suspend fun deleteTopMovies()
}