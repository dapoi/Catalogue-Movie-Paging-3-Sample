package com.dapascript.catmov.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesDao
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesRemoteKeys
import com.dapascript.catmov.data.local.popularmovies.PopularMoviesRemoteKeysDao
import com.dapascript.catmov.data.local.populartv.PopularTVDao
import com.dapascript.catmov.data.local.populartv.PopularTVRemoteKeys
import com.dapascript.catmov.data.local.populartv.PopularTVRemoteKeysDao
import com.dapascript.catmov.data.local.topmovies.TopMoviesDao
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeys
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeysDao
import com.dapascript.catmov.data.local.toptv.TopTVDao
import com.dapascript.catmov.data.local.toptv.TopTVRemoteKeys
import com.dapascript.catmov.data.local.toptv.TopTVRemoteKeysDao
import com.dapascript.catmov.data.remote.model.PopularMoviesItem
import com.dapascript.catmov.data.remote.model.PopularTVItem
import com.dapascript.catmov.data.remote.model.TopMoviesItem
import com.dapascript.catmov.data.remote.model.TopTVItem

@Database(
    entities = [
        TopMoviesItem::class, TopMoviesRemoteKeys::class,
        PopularMoviesItem::class, PopularMoviesRemoteKeys::class,
        TopTVItem::class, TopTVRemoteKeys::class,
        PopularTVItem::class, PopularTVRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {

    // Top Movies
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun remoteKeysDao(): TopMoviesRemoteKeysDao

    // Popular Movies
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun popularMoviesRemoteKeysDao(): PopularMoviesRemoteKeysDao

    // Top TV
    abstract fun topTVDao(): TopTVDao
    abstract fun topTVRemoteKeysDao(): TopTVRemoteKeysDao

    // Popular TV
    abstract fun popularTVDao(): PopularTVDao
    abstract fun popularTVRemoteKeysDao(): PopularTVRemoteKeysDao
}