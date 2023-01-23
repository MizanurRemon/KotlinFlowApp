package com.example.kotlinflowapp.API

import com.example.kotlinflowapp.Model.Post_response
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.Model.User_response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIServices {

    @Headers("app-id:63cd111ff805cea1c4e8e6a3")
    @GET("user")
    suspend fun getUser(
        @Query("limit") limit: String,
        @Query("page") page: String
    ): User_response


    @GET("posts")
    suspend fun getPosts(): List<Post_response>

}