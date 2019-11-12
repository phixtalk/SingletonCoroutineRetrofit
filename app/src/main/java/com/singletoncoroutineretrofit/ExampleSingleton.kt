package com.singletoncoroutineretrofit

import com.singletoncoroutineretrofit.models.User

object ExampleSingleton {

    val singletonUser: User by lazy {
        User("ofoefulec_fny@yahoo.com", "chrisoft", "image.png")
    }
}