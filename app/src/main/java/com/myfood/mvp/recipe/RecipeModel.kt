package com.myfood.mvp.recipe

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class RecipeModel : RecipeContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el idioma actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla Receta
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.RECIPES.int)
    }

    //Metodo que obtiene los atributos de la receta en un idioma dada su id
    override fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity> {
        return myFoodRepository.getRecipe(idRecipe, language)
    }
}