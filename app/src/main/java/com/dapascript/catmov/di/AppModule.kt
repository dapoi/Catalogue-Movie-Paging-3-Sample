package com.dapascript.catmov.di

import android.app.Application
import androidx.room.Room
import com.dapascript.catmov.data.TopMoviesRepository
import com.dapascript.catmov.data.TopMoviesRepositoryImpl
import com.dapascript.catmov.data.local.MediaDatabase
import com.dapascript.catmov.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun databaseProvider(
        app: Application
    ) = Room.databaseBuilder(
        app,
        MediaDatabase::class.java,
        "media_database"
    ).build()

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: ApiService,
        mediaDatabase: MediaDatabase
    ): TopMoviesRepository {
        return TopMoviesRepositoryImpl(apiService, mediaDatabase)
    }
}