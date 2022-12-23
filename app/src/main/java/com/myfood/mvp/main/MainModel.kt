package com.myfood.mvp.main

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class MainModel : MainContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que actualiza el lenguage actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones del menu de navegacion
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }
}