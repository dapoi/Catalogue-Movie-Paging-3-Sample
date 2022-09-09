package com.dapascript.catmov.data.local.popularmovies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dapascript.catmov.data.remote.model.PopularMoviesItem

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMoviesItem>)

    @Query("SELECT * FROM popular_movies")
    fun getPopularMovies(): PagingSource<Int, PopularMoviesItem>

    @Query("DELETE FROM popular_movies")
    suspend fun deletePopularMovies()
}