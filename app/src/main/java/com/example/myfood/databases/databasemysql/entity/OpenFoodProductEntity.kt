package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class OpenFoodProductEntity(
    @SerializedName("product_name") val productName: String,
    @SerializedName("generic_name") val genericName: String,
    @SerializedName("brands") val brands: String,
    @SerializedName("id") val barcode: String,
    @SerializedName("image_front_small_url") val image: String
)