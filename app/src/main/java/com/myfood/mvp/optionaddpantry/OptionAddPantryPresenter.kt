package com.myfood.mvp.optionaddpantry

import android.content.Context


class OptionAddPantryPresenter(
    context: Context
) : OptionAddPantryContract.Presenter {

    //Decalaración de variables globales
    private val optionAddPantryModel: OptionAddPantryModel= OptionAddPantryModel()
    private val currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        optionAddPantryModel.createInstances(context)

        //Obtenemos el idioma actual de la App
        currentLanguage = optionAddPantryModel.getCurrentLanguage()
    }


    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = optionAddPantryModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }
}