package com.fatih.kotlindeveloperjokes.repository

import androidx.compose.ui.semantics.error
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fatih.kotlindeveloperjokes.entity.Joke
import com.fatih.kotlindeveloperjokes.retrofit.RetrofitApi
import com.fatih.kotlindeveloperjokes.util.Result
import kotlinx.coroutines.Dispatchers

class JokeRepository(private val retrofitApi: RetrofitApi) {
    fun getJokes(): LiveData<Result<Joke>> = liveData(Dispatchers.IO) {
        try {
            val response = retrofitApi.getJokes()
            if (response.isSuccessful) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Bilinmeyen bir hata oluştu"))
        }
    }
}