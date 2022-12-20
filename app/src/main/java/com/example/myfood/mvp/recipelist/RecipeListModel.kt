package com.example.myfood.mvp.recipelist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.RecipeListEntity

class RecipeListModel : RecipeListContract.Model {

    private val myFoodRepository = MyFoodRepository()

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