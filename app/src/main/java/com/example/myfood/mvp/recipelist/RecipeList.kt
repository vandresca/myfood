package com.example.myfood.mvp.recipelist

import com.example.myfood.mvvm.data.model.RecipeListEntity

data class RecipeList(
    val id: String,
    val title: String
)

fun RecipeListEntity.toMVP(): List<RecipeList> {
    val list: MutableList<RecipeList> = mutableListOf()
    recipes.forEach {
        list += RecipeList(it.id, it.title)
    }
    return list
}