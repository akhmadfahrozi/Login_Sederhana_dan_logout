package com.ozi.myapplication.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewtworkClass {
    //sesuaikan akses domain,ga boleh localhost(harus domain atau ip)
    const val BASE_URL = "http://akhmad-fahrozi.com/lauwbanews/index.php/json/"
    //////////////////////////////////////////////////////////////////////////////////
    fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
    }


    fun getService(): ApiService {
        return getRetrofit().create<ApiService>(ApiService::class.java)
    }
}