package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa la lista de productos dependiendo su
// caducidad
class ExpirationListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("products") val products: List<ExpirationListProductEntity>
)