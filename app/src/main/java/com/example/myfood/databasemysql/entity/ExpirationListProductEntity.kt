package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class ExpirationListProductEntity(
    @SerializedName("name") val name: String,
    @SerializedName("days") val days: String,
    @SerializedName("price") val price: String,
    @SerializedName("expiration") val expiration: String
)