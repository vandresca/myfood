package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Entidad que serializa una lista de recetas
class RecipeListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("recipes") val recipes: List<RecipeListProductEntity>
)