package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Entidad que serializa el grupo de tipos de nutrientes de un producto
class NutrientGroupEntity(
    @SerializedName("response") val status: String,
    @SerializedName("nutrients") val nutrients: List<String>
)