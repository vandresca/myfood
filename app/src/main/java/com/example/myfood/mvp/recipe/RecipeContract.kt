package com.example.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.RecipeEntity

interface RecipeContract {
    interface View : Translatable.View {
        fun onRecipeLoaded(recipeEntity: RecipeEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun getRecipe(idRecipe: String, idLanguage: String): MutableLiveData<RecipeEntity>
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity>
    }
}