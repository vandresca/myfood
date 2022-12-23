package com.myfood.mvp.addquantityunit

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class AddQuantityUnitModel : AddQuantityUnitContract.Model {

    //Declaramos una instancia del repositorio de metodos de acceso a la base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que instancia la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguaje actual de base de datos
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones para la pantalla de Unidades de cantidad
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    //Metodo que añade una unidad de cantidad a la base de datos SQLite
    override fun addQuantityUnit(quantityUnit: String) {
        myFoodRepository.addQuantityUnit(quantityUnit)
    }

    //Metodo que actualiza una unidad de cantidad en la base de datos SQLite a partir
    //de su id
    override fun updateQuantityUnit(quantityUnit: String, id: String) {
        myFoodRepository.updateQuantityUnit(quantityUnit, id)
    }
}