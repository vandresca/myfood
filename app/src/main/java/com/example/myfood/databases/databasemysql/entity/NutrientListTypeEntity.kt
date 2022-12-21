package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa los nutrientes a partir de un tipo de grupo
// de nutrientes determinado de un producto
class NutrientListTypeEntity(
    @SerializedName("response") val status: String,
    @SerializedName("food_nutrients") val foodNutrients: List<NutrientTypeEntity>
)