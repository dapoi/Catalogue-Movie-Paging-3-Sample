package com.dapascript.catmov.data.local.toptv

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dapascript.catmov.data.remote.model.TopTVItem

@Dao
interface TopTVDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopTV(topMovies: List<TopTVItem>)

    @Query("SELECT * FROM top_tv")
    fun getTopTV(): PagingSource<Int, TopTVItem>

    @Query("DELETE FROM top_tv")
    suspend fun deleteTopTV()
}