package com.myfood.mvp.recipelist

import com.myfood.databases.databasemysql.entity.RecipeListEntity

data class RecipeList(
    val id: String,
    val title: String
)

//Funcion que transforma el objeto RecipeListEntity a una lista de objetos RecipeList
fun RecipeListEntity.toMVP(): List<RecipeList> {
    val list: MutableList<RecipeList> = mutableListOf()
    recipes.forEach {
        list += RecipeList(it.id, it.title)
    }
    return list
}