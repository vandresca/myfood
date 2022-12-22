package com.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class RecipeModel : RecipeContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.RECIPES.int)
    }

    override fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity> {
        return myFoodRepository.getRecipe(idRecipe, language)
    }
}