package com.dapascript.catmov.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class PopularMoviesResponse(

    @Json(name = "page")
    val page: Int,

    @Json(name = "total_pages")
    val total_pages: Int,

    @Json(name = "results")
    val results: List<PopularMoviesItem>,

    @Json(name = "total_results")
    val total_results: Int
)

@Entity(tableName = "popular_movies")
data class PopularMoviesItem(

    @Json(name = "overview")
    val overview: String,

    @Json(name = "original_language")
    val original_language: String,

    @Json(name = "original_title")
    val original_title: String,

    @Json(name = "video")
    val video: Boolean,

    @Json(name = "title")
    val title: String,

    @Json(name = "poster_path")
    val poster_path: String,

    @Json(name = "backdrop_path")
    val backdrop_path: String,

    @Json(name = "release_date")
    val release_date: String,

    @Json(name = "popularity")
    val popularity: Double,

    @Json(name = "vote_average")
    val vote_average: Double,

    @PrimaryKey
    @Json(name = "id")
    val id: Int,

    @Json(name = "adult")
    val adult: Boolean,

    @Json(name = "vote_count")
    val vote_count: Int
)
