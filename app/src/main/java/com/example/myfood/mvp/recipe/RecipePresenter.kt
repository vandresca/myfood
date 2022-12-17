package com.example.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.RecipeEntity


class RecipePresenter(
    private val recipeView: RecipeContract.View,
    private val recipeModel: RecipeContract.Model,
    context: Context
) : RecipeContract.Presenter {

    init {
        recipeModel.getInstance(context)
    }

    override fun getRecipe(idRecipe: String, idLanguage: String): MutableLiveData<RecipeEntity> {
        return recipeModel.getRecipe(idRecipe, idLanguage)
    }

    override fun getCurrentLanguage(): String {
        return recipeModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = recipeModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }
}
