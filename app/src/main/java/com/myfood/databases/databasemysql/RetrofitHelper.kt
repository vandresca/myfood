package com.myfood.databases.databasemysql

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    // Obtiene una instancia de Retrofit para el servicio API de PHP
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://vandresc.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}