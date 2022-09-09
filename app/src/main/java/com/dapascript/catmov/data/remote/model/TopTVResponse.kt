package com.dapascript.catmov.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class TopTVResponse(

    @Json(name = "page")
    val page: Int,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "results")
    val results: List<TopTVItem>,

    @Json(name = "total_results")
    val total_results: Int
)

@Entity(tableName = "top_tv")
data class TopTVItem(

    @Json(name = "first_air_date")
    val first_air_date: String,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "original_language")
    val original_language: String,

    @Json(name = "poster_path")
    val poster_path: String,

    @Json(name = "original_name")
    val original_name: String,

    @Json(name = "popularity")
    val popularity: Double,

    @Json(name = "vote_average")
    val vote_average: Double,

    @Json(name = "name")
    val name: String,

    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "vote_count")
    val vote_count: Int
)
