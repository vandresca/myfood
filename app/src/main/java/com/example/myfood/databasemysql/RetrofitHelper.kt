package com.example.myfood.mvvm.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://vandresc.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}