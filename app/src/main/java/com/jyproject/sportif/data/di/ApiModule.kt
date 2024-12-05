package com.jyproject.sportif.data.di

import com.jyproject.sportif.BuildConfig
import com.jyproject.sportif.data.remote.service.naverMapGeocode.NaverMapGeocodeService
import com.jyproject.sportif.data.remote.service.searchChair.SearchChairService
import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import com.jyproject.sportif.data.remote.service.searchNearSportFacility.SearchNearSportFacilityService
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
        const val NAVER_MAP_URL = "https://naveropenapi.apigw.ntruss.com/"
        const val CHAT_URL = "https://d263-59-15-203-189.ngrok-free.app/"
    }

    private val naverMapHeader = mapOf(
        "x-ncp-apigw-api-key-id" to BuildConfig.NAVER_MAP_CLIENT_ID,
        "x-ncp-apigw-api-key" to BuildConfig.NAVER_MAP_CLIENT_KEY
    )

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChatRetrofit

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

    @NaverMapRetrofit
    @Singleton
    @Provides
    fun getNaverOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(OkhttpInterceptor(naverMapHeader))
            .build()
    }

    @ChatRetrofit
    @Singleton
    @Provides
    fun getChatOkHttpClient(): OkHttpClient {
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

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(SEOUL_URL)
            .build()
    }

    @NaverMapRetrofit
    @Singleton
    @Provides
    fun getNaverMapInstance(@NaverMapRetrofit okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(NAVER_MAP_URL)
            .build()
    }

    @ChatRetrofit
    @Singleton
    @Provides
    fun getChatInstance(@ChatRetrofit okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(CHAT_URL)
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

    @Singleton
    @Provides
    fun provideNaverMapGeocode(@NaverMapRetrofit retrofit: Retrofit): NaverMapGeocodeService {
        return retrofit.create(NaverMapGeocodeService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchNearSportFacility(@SeoulRetrofit retrofit: Retrofit): SearchNearSportFacilityService {
        return retrofit.create(SearchNearSportFacilityService::class.java)
    }
}
