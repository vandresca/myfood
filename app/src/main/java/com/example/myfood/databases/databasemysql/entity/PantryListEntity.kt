package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class PantryListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("products") val products: List<PantryListProductEntity>
)