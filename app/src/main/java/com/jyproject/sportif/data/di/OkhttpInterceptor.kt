package com.jyproject.sportif.data.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

// 헤더를 추가하는 인터셉터
class OkhttpInterceptor(private val headers: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        // 헤더를 로그로 출력
        Log.d("OkhttpInterceptor", "Request Headers:")
        headers.forEach { (key, value) ->
            Log.d("OkhttpInterceptor", "$key: $value")
        }
        val requestBuilder = originalRequest.newBuilder()

        // 헤더 추가
        headers.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}