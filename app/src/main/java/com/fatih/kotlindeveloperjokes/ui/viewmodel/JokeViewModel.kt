package com.fatih.kotlindeveloperjokes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fatih.kotlindeveloperjokes.repository.JokeRepository
import com.fatih.kotlindeveloperjokes.util.Utils

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {

    val jokes = repository.getJokes()

    companion object{
        val factory : ViewModelProvider.Factory = viewModelFactory {
            addInitializer(JokeViewModel::class){
                JokeViewModel(JokeRepository(Utils.retrofitApi))
            }
        }
    }

}