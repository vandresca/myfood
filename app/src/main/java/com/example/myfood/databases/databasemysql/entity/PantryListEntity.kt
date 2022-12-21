package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa l una lista de productos de despensa
class PantryListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("products") val products: List<PantryListProductEntity>
)