package com.example.myfood.mvp.shoplist

import com.example.myfood.mvvm.data.model.ShopListEntity

data class ShopList(
    val id: String,
    val name: String,
    val quantity: String,
    val quantityUnit: String
)

fun ShopListEntity.toMVP(): List<ShopList> {
    val list: MutableList<ShopList> = mutableListOf()
    products.forEach {
        list += ShopList(it.id, it.name, it.quantity, it.quantityUnit)
    }
    return list
}