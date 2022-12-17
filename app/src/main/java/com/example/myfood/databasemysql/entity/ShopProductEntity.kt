package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class ShopProductEntity(
    @SerializedName("response") val status: String,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("quantityUnit") val quantityUnit: String
)