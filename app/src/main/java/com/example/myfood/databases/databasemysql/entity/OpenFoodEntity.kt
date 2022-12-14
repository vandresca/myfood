package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que seriaiza los datos de la petición a la API de OpenFood
data class OpenFoodEntity(
    @SerializedName("status") val status: Int,
    @SerializedName("product") val product: OpenFoodProductEntity
)