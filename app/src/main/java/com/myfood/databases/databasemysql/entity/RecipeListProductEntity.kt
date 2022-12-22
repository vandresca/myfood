package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

//Subentidad de RecipeListEntity
class RecipeListProductEntity(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String
)