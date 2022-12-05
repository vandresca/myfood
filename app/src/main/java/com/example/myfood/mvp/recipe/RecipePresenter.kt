package com.example.myfood.mvp.recipe

import android.os.Handler
import android.os.Looper
import org.json.JSONObject

class RecipePresenter(
    private val recipeView: RecipeContract.View,
    private val recipeModel: RecipeContract.Model,
    private var idRecipe: String
) : RecipeContract.Presenter {

    init {
        recipeModel.getRecipe(this, idRecipe, "1")
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response)
        Handler(Looper.getMainLooper()).post {
            recipeView.showRecipe(
                Recipe(
                    json.get("title").toString(),
                    json.get("portions").toString(),
                    json.get("ingredients").toString(),
                    json.get("directions").toString(),
                )
            )
        }
    }
}
