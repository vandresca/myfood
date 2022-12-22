package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

//Subentidad de ExpirationListEntity
class ExpirationListProductEntity(
    @SerializedName("name") val name: String,
    @SerializedName("days") val days: String,
    @SerializedName("price") val price: String,
    @SerializedName("expiration") val expiration: String
)