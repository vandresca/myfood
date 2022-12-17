package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class RecipeListProductEntity(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)