package com.example.fetch.api

import com.example.fetch.api.model.FetchDataObject
import retrofit2.Response
import retrofit2.http.GET

interface FetchDataService {

    @GET("hiring.json")
    suspend fun getFetchData() : Response<List<FetchDataObject>>

}