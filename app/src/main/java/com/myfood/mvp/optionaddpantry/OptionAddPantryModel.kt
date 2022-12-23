package com.myfood.mvp.optionaddpantry

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class OptionAddPantryModel : OptionAddPantryContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la base de datos
    override fun createInstances(application: Context) {
        myFoodRepository.createInstances(application)
    }

    //Metodo que devuelve el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que devuelve las traducciones de la pantalla de opcion entre escanea e
    //inserción manual
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }
}