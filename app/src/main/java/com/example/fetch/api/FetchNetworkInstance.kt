package com.example.fetch.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchNetworkInstance {

    companion object RetrofitInstance {

        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

        fun getRetrifitService() : FetchDataService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
                .create(FetchDataService::class.java)
        }

    }

}