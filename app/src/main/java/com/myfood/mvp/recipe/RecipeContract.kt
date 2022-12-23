package com.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Recetas
interface RecipeContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta una vez que tenemos de base de datos los atributos
        //de la receta
        fun onRecipeLoaded(recipeEntity: RecipeEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que obtiene los atributos de la receta en un idioma dada su id
        fun getRecipe(idRecipe: String, idLanguage: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene los atributos de la receta en un idioma dada su id
        fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity>
    }
}