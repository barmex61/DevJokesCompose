package com.fatih.kotlindeveloperjokes.util

import com.fatih.kotlindeveloperjokes.repository.JokeRepository
import com.fatih.kotlindeveloperjokes.retrofit.RetrofitApi
import com.fatih.kotlindeveloperjokes.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Utils {

    private val gsonConverterSingleton : GsonConverterFactory = GsonConverterFactory.create()
    private val retrofitSingleton : Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        gsonConverterSingleton
    ).build()
    val retrofitApi : RetrofitApi = retrofitSingleton.create(RetrofitApi::class.java)
    val jokeRepository : JokeRepository = JokeRepository(retrofitApi)
}