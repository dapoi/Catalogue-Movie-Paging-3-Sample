package com.dapascript.catmov.di

import android.app.Application
import androidx.room.Room
import com.dapascript.catmov.data.MoviesRepository
import com.dapascript.catmov.data.MoviesRepositoryImpl
import com.dapascript.catmov.data.TVRepository
import com.dapascript.catmov.data.TVRepositoryImpl
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
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: ApiService,
        mediaDatabase: MediaDatabase
    ): MoviesRepository {
        return MoviesRepositoryImpl(apiService, mediaDatabase)
    }

    @Provides
    @Singleton
    fun provideTVRepository(
        apiService: ApiService,
        mediaDatabase: MediaDatabase
    ): TVRepository {
        return TVRepositoryImpl(apiService, mediaDatabase)
    }
}