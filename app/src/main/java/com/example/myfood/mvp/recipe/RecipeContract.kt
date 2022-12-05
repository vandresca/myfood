package com.example.myfood.mvp.recipe

interface RecipeContract {
    interface View {
        fun showRecipe(recipe: Recipe)
    }

    interface Presenter {
        fun loadData(response: String?)
    }

    interface Model {
        fun getRecipe(application: RecipePresenter, idRecipe: String, language: String)
    }
}