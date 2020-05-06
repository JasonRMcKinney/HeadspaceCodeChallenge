package com.example.headspacecodechallenge.network

import com.example.headspacecodechallenge.model.ImageResponseItem
import com.example.headspacecodechallenge.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface WebService {
    companion object {
        val instance: WebService by lazy {
            //Logging
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //OkHttp
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
            //Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            retrofit.create(WebService::class.java)
        }
    }

    @GET(Constants.endpoint)
    fun getImages(): List<ImageResponseItem>
}