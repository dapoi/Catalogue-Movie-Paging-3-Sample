package com.dapascript.catmov.data.local.populartv

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dapascript.catmov.data.remote.model.PopularTVItem

@Dao
interface PopularTVDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularTV(movies: List<PopularTVItem>)

    @Query("SELECT * FROM popular_tv")
    fun getPopularTV(): PagingSource<Int, PopularTVItem>

    @Query("DELETE FROM popular_tv")
    suspend fun deletePopularTV()
}