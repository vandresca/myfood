package com.myfood.mvp.storeplacelist

import android.content.Context
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class StorePlaceListModel : StorePlaceListContract.Model {

    ///Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el idioma actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones para la pantalla de Lugares de ALmacenaje
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    //Metodo que obtiene los lugares de almacenaje de la base de datos SQLite
    override fun getStorePlaces(): List<StorePlace> {
        return myFoodRepository.getStorePlaces()
    }

    //Metodo que borra un lugra de almacenaje de la base de datos SQLite dado su id
    override fun deleteStorePlace(idPlace: String) {
        myFoodRepository.deleteStorePlace(idPlace)
    }
}