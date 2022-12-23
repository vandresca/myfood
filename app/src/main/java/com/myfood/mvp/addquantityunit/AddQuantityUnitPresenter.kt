package com.myfood.mvp.addquantityunit

import android.content.Context


class AddQuantityUnitPresenter(
    context: Context
) : AddQuantityUnitContract.Presenter {

    //Declaramos las variables globales
    private val addQuantityUnitModel: AddQuantityUnitModel = AddQuantityUnitModel()

    init {

        //Creamos las instancias de la base de datos
        addQuantityUnitModel.createInstances(context)
    }


    //Metodo que retorna las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val currentLanguage = addQuantityUnitModel.getCurrentLanguage()
        val translations = addQuantityUnitModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que añade una cantidad a la base de datos SQLite
    override fun addQuantityUnit(quantityUnit: String) {
        addQuantityUnitModel.addQuantityUnit(quantityUnit)
    }

    //Metodo que actualiza una cantidad en la base de datos SQLite a partir de su id
    override fun updateQuantityUnit(quantityUnit: String, idQuantityUnit: String) {
        addQuantityUnitModel.updateQuantityUnit(quantityUnit, idQuantityUnit)
    }
}