package com.myfood.mvp.recipelist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.RecipeListEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class RecipeListModel : RecipeListContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el id de usuario actual de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene el idioma actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla Recetas
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.RECIPES.int)
    }

    //Metodo que obtiene la lista de todas las recetas en el idioma actual
    override fun getRecipeList(language: String): MutableLiveData<RecipeListEntity> {
        return myFoodRepository.getRecipeList(language)
    }

    //Metodo que obtiene una lista de recetas en el idioma actual con los productos
    //de que se dispone en despensa
    override fun getRecipesSuggested(
        language: String,
        user: String
    ): MutableLiveData<RecipeListEntity> {
        return myFoodRepository.getRecipesSuggested(language, user)
    }
}