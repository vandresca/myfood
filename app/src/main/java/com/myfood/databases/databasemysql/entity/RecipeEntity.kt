package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Entidad que serializa los atributos de una receta
class RecipeEntity(
    @SerializedName("response") val status: String,
    @SerializedName("title") val title: String,
    @SerializedName("portions") val portions: String,
    @SerializedName("ingredients") val ingredients: String,
    @SerializedName("directions") val directions: String,

    )