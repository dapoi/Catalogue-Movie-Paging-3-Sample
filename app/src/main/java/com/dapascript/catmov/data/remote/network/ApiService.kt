package com.dapascript.catmov.data.remote.network

import com.dapascript.catmov.data.remote.model.PopularMoviesResponse
import com.dapascript.catmov.data.remote.model.PopularTVResponse
import com.dapascript.catmov.data.remote.model.TopMoviesResponse
import com.dapascript.catmov.data.remote.model.TopTVResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "4f948dc2d121184b17586b04a38b778a",
    ): TopMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "4f948dc2d121184b17586b04a38b778a",
    ): PopularMoviesResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTV(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "4f948dc2d121184b17586b04a38b778a",
    ): TopTVResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "4f948dc2d121184b17586b04a38b778a",
    ): PopularTVResponse
}