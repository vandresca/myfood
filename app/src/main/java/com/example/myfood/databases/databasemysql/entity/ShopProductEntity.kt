package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa los atributos de un producto de compra
class ShopProductEntity(
    @SerializedName("response") val status: String,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("quantityUnit") val quantityUnit: String
)