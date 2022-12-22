package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Subentidad de NutrientListTypeEntity
class NutrientTypeEntity(
    @SerializedName("column") val column: String,
    @SerializedName("value") val value: String
)