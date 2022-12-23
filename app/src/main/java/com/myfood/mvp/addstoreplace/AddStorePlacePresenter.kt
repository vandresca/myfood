package com.myfood.mvp.addstoreplace

import android.content.Context

class AddStorePlacePresenter(
    context: Context
) : AddStorePlaceContract.Presenter {

    //Declaramos las variables globales
    private val addStorePlaceModel: AddStorePlaceModel= AddStorePlaceModel()

    init {

        //Creamos las instancias de la base de datos
        addStorePlaceModel.createInstances(context)
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val currentLanguage = addStorePlaceModel.getCurrentLanguage()
        val translations = addStorePlaceModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que añade un lugar de almacenaje en la base de datos SQLite
    override fun addStorePlace(storePlace: String) {
        addStorePlaceModel.addStorePlace(storePlace)
    }

    //Metodo que actualiza un lugar de almacenaje en la base de datos SQLite dado su id
    override fun updateStorePlace(storePlace: String, idStorePlace: String) {
        addStorePlaceModel.updateStorePlace(storePlace, idStorePlace)
    }
}