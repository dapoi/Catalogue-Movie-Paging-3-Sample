package com.dapascript.catmov.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeys
import com.dapascript.catmov.data.local.topmovies.TopMoviesRemoteKeysDao
import com.dapascript.catmov.data.local.topmovies.TopMoviesDao
import com.dapascript.catmov.data.remote.model.TopMoviesItem

@Database(
    entities = [TopMoviesItem::class, TopMoviesRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun remoteKeysDao(): TopMoviesRemoteKeysDao
}