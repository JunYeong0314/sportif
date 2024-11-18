package com.jyproject.sportif.data.di

import com.jyproject.sportif.data.remote.service.searchChair.SearchChairService
import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    companion object {
        const val SEOUL_URL = "https://apis.data.go.kr/B551014/"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulRetrofit

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulInstance(@SeoulRetrofit okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder().client(okHttpClient)
            .client(getSeoulOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(SEOUL_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchFacility(@SeoulRetrofit retrofit: Retrofit): SearchFacilityService {
        return retrofit.create(SearchFacilityService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchChair(@SeoulRetrofit retrofit: Retrofit): SearchChairService {
        return retrofit.create(SearchChairService::class.java)
    }
}
