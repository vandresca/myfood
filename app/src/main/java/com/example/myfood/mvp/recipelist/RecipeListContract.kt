package com.example.myfood.mvp.recipelist

import android.content.Context
import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.RecipeListEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Lista Recetas
interface RecipeListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun initRecyclerView(recipeListAdapter: RecipeListAdapter)
        fun loadFragment(fragment: Fragment)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun loadRecipes(result: RecipeListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
        fun getUserId(): String
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getUserId(): String
        fun getRecipeList(language: String): MutableLiveData<RecipeListEntity>
        fun getRecipesSuggested(language: String, user: String): MutableLiveData<RecipeListEntity>
    }
}