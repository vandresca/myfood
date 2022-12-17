package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class OpenFoodEntity(
    @SerializedName("status") val status: Int,
    @SerializedName("product") val product: OpenFoodProductEntity
)