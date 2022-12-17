package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class NutrientListTypeEntity(
    @SerializedName("response") val status: String,
    @SerializedName("food_nutrients") val foodNutrients: List<NutrientTypeEntity>
)