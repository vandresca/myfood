package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class ShopListProductEntity(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("quantityUnit") val quantityUnit: String
)