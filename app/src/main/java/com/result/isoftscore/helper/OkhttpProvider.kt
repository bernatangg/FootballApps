package com.result.isoftscore.helper

import com.result.isoftscore.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkhttpProvider {
    companion object {
        private var instance: OkHttpClient? = null

        fun getInstance(): OkHttpClient {
            if(instance == null) {
                instance = OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE })
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .writeTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .build()
            }
            return instance as OkHttpClient
        }
    }
}

