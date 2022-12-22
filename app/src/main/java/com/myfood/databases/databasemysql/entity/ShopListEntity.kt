package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Entidad que serializa una lista de productos de compra
class ShopListEntity(
    @SerializedName("response") val status: String,
    @SerializedName("products") val products: List<ShopListProductEntity>
)