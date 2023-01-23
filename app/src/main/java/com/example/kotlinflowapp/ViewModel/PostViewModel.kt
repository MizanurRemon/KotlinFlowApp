package com.example.kotlinflowapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowapp.Model.Post_response
import com.example.kotlinflowapp.Model.User_data_response
import com.example.kotlinflowapp.Model.User_response
import com.example.kotlinflowapp.Repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    val responseLiveData: MutableLiveData<List<Post_response>> = MutableLiveData()

    val responseUserLiveData: MutableLiveData<User_response> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            UserRepository.getPosts()
                .catch { e ->
                    Log.d("data", "ERROR ${e.message}")
                }.collect { response ->
                    responseLiveData.value = response
                }
        }
    }

    fun getUsers(limit: String, page: String) {
        viewModelScope.launch {
            UserRepository.getUsers(limit, page)
                .catch {e->
                    Log.d("dataxx", "ERROR ${e.message}")
                }.collect { response ->
                    responseUserLiveData.value = response
                }
        }
    }
}
