package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class RecipeEntity(
    @SerializedName("response") val status: String,
    @SerializedName("title") val title: String,
    @SerializedName("portions") val portions: String,
    @SerializedName("ingredients") val ingredients: String,
    @SerializedName("directions") val directions: String,

    )