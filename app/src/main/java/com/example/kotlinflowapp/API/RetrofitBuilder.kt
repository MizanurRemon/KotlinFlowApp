package com.example.kotlinflowapp.API

import com.example.kotlinflowapp.Utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitBuilder {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: APIServices by lazy {
        retrofit.create(APIServices::class.java)
    }
}