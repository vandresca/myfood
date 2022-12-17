package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class NutrientTypeEntity(
    @SerializedName("column") val column: String,
    @SerializedName("value") val value: String
)