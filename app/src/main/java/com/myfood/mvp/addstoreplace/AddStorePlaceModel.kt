package com.myfood.mvp.addstoreplace

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class AddStorePlaceModel : AddStorePlaceContract.Model {

    //Declaramos una instancia del repositorio de metodos de acceso a la base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que instancia la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que añade un lugar de almacenaje a la base de datos SQLite
    override fun addStorePlace(storePlace: String) {
        myFoodRepository.addStorePlace(storePlace)
    }

    //Metodo que actualiza un lugar de almacenaje en la base de datos SQLite dado su id
    override fun updateStorePlace(storePlace: String, id: String) {
        myFoodRepository.updateStorePlace(storePlace, id)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones para la pantalla de lugares de almacenaje
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }
}