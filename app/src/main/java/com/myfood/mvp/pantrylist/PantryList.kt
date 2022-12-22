package com.myfood.mvp.pantrylist

import com.myfood.databases.databasemysql.entity.PantryListEntity

data class PantryList(
    val id: String,
    val name: String,
    val quantity: String,
    val quantityUnit: String,
    val price: String
)

fun PantryListEntity.toMVP(): List<PantryList> {
    val list: MutableList<PantryList> = mutableListOf()
    products.forEach {
        list += PantryList(it.id, it.name, it.quantity, it.quantityUnit, it.price)
    }
    return list
}