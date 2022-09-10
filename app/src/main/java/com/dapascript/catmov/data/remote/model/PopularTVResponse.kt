package com.dapascript.catmov.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class PopularTVResponse(

    @Json(name = "page")
    val page: Int,

    @Json(name = "total_pages")
    val total_pages: Int,

    @Json(name = "results")
    val results: List<PopularTVItem>,

    @Json(name = "total_results")
    val total_results: Int
)

@Entity(tableName = "popular_tv")
data class PopularTVItem(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "poster_path")
    val poster_path: String,

    @Json(name = "vote_average")
    val vote_average: Double,

    @Json(name = "name")
    val name: String,
)
