package com.app.employeeservice.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // =========================================================
    // BASE URL
    // =========================================================

    private const val BASE_URL =
        "https://employee.app.com.pk/"


    // =========================================================
    // LOGGING
    // =========================================================

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {

            level =
                HttpLoggingInterceptor.Level.BODY
        }


    // =========================================================
    // OKHTTP CLIENT
    // =========================================================

    private val okHttpClient =
        OkHttpClient.Builder()

            .addInterceptor(
                loggingInterceptor
            )

            .connectTimeout(
                30,
                TimeUnit.SECONDS
            )

            .readTimeout(
                30,
                TimeUnit.SECONDS
            )

            .writeTimeout(
                30,
                TimeUnit.SECONDS
            )

            .build()


    // =========================================================
    // RETROFIT
    // =========================================================

    private val retrofit =
        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .client(okHttpClient)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()


    // =========================================================
    // API SERVICE
    // =========================================================

    val apiService: ApiService =
        retrofit.create(
            ApiService::class.java
        )
}