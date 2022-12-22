package com.myfood.mvp.expiration

import com.myfood.databases.databasemysql.entity.ExpirationListEntity

data class ExpirationList(
    val name: String,
    val days: String,
    val expiration: String,
    val price: String
)

fun ExpirationListEntity.toMVP(): List<ExpirationList> {
    val list: MutableList<ExpirationList> = mutableListOf()
    products.forEach {
        list += ExpirationList(it.name, it.days, it.expiration, it.price)
    }
    return list
}