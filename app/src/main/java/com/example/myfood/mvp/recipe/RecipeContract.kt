package com.example.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.RecipeEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Recetas
interface RecipeContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onRecipeLoaded(recipeEntity: RecipeEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun getRecipe(idRecipe: String, idLanguage: String): MutableLiveData<RecipeEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity>
    }
}