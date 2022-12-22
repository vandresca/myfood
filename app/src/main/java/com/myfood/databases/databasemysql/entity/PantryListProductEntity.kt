package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

//Subentidad de PantryListEntity
class PantryListProductEntity(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("quantityUnit") val quantityUnit: String,
    @SerializedName("price") val price: String,
)