package com.myfood.mvp.quantityunitlist

import android.content.Context
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class QuantityUnitListModel : QuantityUnitListContract.Model {

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

    //Metodo que obtiene las traducciones para la pantalla de Unidades de Cantidad
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    //Metodo que obtiene las unidades de cantidd de la App
    override fun getQuantityUnits(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    //Metodo que elimina una unidad de cantidad de la base de datos SQLite
    override fun deleteQuantityUnit(idQuantityUnit: String) {
        myFoodRepository.deleteQuantityUnit(idQuantityUnit)
    }
}