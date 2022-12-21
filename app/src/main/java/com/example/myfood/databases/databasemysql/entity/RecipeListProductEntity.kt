package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

//Subentidad de RecipeListEntity
class RecipeListProductEntity(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)