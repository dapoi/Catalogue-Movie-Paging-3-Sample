package com.dapascript.catmov.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dapascript.catmov.data.remote.model.TopMoviesItem

@Database(
    entities = [TopMoviesItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class TopMoviesDatabase : RoomDatabase() {
    abstract fun topMoviesDao(): TopMoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}