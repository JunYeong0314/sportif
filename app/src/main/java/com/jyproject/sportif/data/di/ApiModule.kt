package com.jyproject.sportif.data.di

import com.jyproject.sportif.data.remote.service.searchFacility.SearchFacilityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    companion object {
        const val SEOUL_URL = "http://apis.data.go.kr/B551014/SRVC_OD_API_FACIL_MNG_DVOUCHER"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulRetrofit

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @SeoulRetrofit
    @Singleton
    @Provides
    fun getSeoulInstance(@SeoulRetrofit okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .client(getSeoulOkHttpClient())
            .baseUrl(SEOUL_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchFacility(@SeoulRetrofit retrofit: Retrofit): SearchFacilityService {
        return  retrofit.create(SearchFacilityService::class.java)
    }
}
