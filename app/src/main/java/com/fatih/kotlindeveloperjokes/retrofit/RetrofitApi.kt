package com.fatih.kotlindeveloperjokes.retrofit

import com.fatih.kotlindeveloperjokes.entity.Joke
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {

    @GET("atilsamancioglu/ProgrammingJokesJSON/refs/heads/main/jokes.json")
    suspend fun getJokes() : Response<Joke>
}