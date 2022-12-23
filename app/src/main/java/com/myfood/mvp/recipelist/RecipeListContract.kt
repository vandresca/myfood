package com.myfood.mvp.recipelist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeListEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Lista Recetas
interface RecipeListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que inicializa el recyclerview
        fun initRecyclerView(recipeListAdapter: RecipeListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que se ejecuta cuando se tienen la lista de recetas de la base de datos
        fun loadRecipes(result: RecipeListEntity)

        //Metodo que se ejecuta cuando se produce un cambio de texto en el campo de texto
        //del buscador
        fun doFilter(userFilter: Editable?)

        //Metodo que filtra todas las recetas de sistema
        fun filterAll()

        //Metodo que filtra las recetas en el idioma actual que dispongan de los productos
        //que el usuario tiene en despensa
        fun filterSuggested()
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de base de datos
        fun createInstances(context: Context)

        //Metodo que obtiene el usuario actual de la app
        fun getUserId(): String

        //Metodo que obtiene la lista de todas las recetas del sistema
        fun getRecipeList(language: String): MutableLiveData<RecipeListEntity>

        //Metodo que obtiene las recetas en el idioma actual que dispongan de los productos
        //que el usuario tiene en despensa
        fun getRecipesSuggested(language: String, user: String): MutableLiveData<RecipeListEntity>
    }
}