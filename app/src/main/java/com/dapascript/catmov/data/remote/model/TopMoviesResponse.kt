package com.dapascript.catmov.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class TopMoviesResponse(

    @Json(name = "page")
    val page: Int,

    @Json(name = "total_pages")
    val total_pages: Int,

    @Json(name = "results")
    val results: List<TopMoviesItem>,

    @Json(name = "total_results")
    val total_results: Int
)

@Entity(tableName = "top_movies")
data class TopMoviesItem(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "overview")
    val overview: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "poster_path")
    val poster_path: String,

    @Json(name = "vote_average")
    val vote_average: Double,
)
