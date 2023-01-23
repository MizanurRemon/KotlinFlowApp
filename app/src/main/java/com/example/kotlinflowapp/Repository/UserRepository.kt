package com.example.kotlinflowapp.Repository

import android.util.Log
import com.example.kotlinflowapp.API.RetrofitBuilder
import com.example.kotlinflowapp.Model.Post_response
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.Model.User_response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {

    companion object{
        fun getPosts():Flow<List<Post_response>> = flow {
            val response = RetrofitBuilder.api.getPosts()

            emit(response)
        }.flowOn(Dispatchers.IO)

        fun getUsers(limit: String, page: String): Flow<User_response> = flow {
            val response = RetrofitBuilder.api.getUser(limit,page)

            Log.d("dataxx", "response ${response.toString()}")

            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}