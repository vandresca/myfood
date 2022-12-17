package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class RecipeListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("recipes") val recipes: List<RecipeListProductEntity>
)