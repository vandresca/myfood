package com.myfood.mvp.main

class MainPresenter(
    mainActivity: MainActivity
) : MainContract.Presenter {

    //Declaramos las variables globales
    private var mainModel:MainModel= MainModel()
    private var currentLanguage: String

    init {

        //Creamos las instancias en las bases de datos
        mainModel.createInstances(mainActivity)

        //Obtenemos el lenguage actual de la app
        currentLanguage = mainModel.getCurrentLanguage()
    }

    //Metodo que devuelve las traducciones del menu de navegacion en el idioma actual
    override fun getTranslationsMenu():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = mainModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }
}