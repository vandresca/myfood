package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class OneValueEntity(
    @SerializedName("response") val status: String,
    @SerializedName("value") val value: String
)