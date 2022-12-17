package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class NutrientGroupEntity(
    @SerializedName("response") val status: String,
    @SerializedName("nutrients") val nutrients: List<String>
)