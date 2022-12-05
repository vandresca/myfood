package com.example.myfood.mvp.recipe

import com.example.myfood.rest.MySQLREST

class RecipeModel : RecipeContract.Model {
    override fun getRecipe(application: RecipePresenter, idRecipe: String, language: String) {
        MySQLREST.getRecipe(application, idRecipe, language)
    }
}