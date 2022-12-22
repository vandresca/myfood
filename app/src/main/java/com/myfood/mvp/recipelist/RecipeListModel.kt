package com.myfood.mvp.recipelist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeListEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class RecipeListModel : RecipeListContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.RECIPES.int)
    }

    override fun getRecipeList(language: String): MutableLiveData<RecipeListEntity> {
        return myFoodRepository.getRecipeList(language)
    }

    override fun getRecipesSuggested(
        language: String,
        user: String
    ): MutableLiveData<RecipeListEntity> {
        return myFoodRepository.getRecipesSuggested(language, user)
    }
}