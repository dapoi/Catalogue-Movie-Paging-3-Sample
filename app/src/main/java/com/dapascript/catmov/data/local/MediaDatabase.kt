package com.dapascript.catmov.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesDao
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesRemoteKeys
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesRemoteKeysDao
import com.dapascript.catmov.data.local.topmovies.TopMoviesDao
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeys
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeysDao
import com.dapascript.catmov.data.remote.model.PopularMoviesItem
import com.dapascript.catmov.data.remote.model.TopMoviesItem

@Database(
    entities = [
        TopMoviesItem::class, TopMoviesRemoteKeys::class,
        PopularMoviesItem::class, PopularMoviesRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {

    // Top Movies
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun remoteKeysDao(): TopMoviesRemoteKeysDao

    // Now Playing
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularMoviesRemoteKeysDao(): PopularMoviesRemoteKeysDao
}