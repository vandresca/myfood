package com.example.myfood.mvp.pantryfeature

import android.content.Context

class PantryNutrientPresenter(
    private val pantryNutrientFragment: PantryNutrientFragment,
    context: Context
) : PantryNutrientContract.Presenter {

    //Declaramos las variables globales
    private val pantryNutrientModel: PantryNutrientModel = PantryNutrientModel()
    private val currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        pantryNutrientModel.createInstances(context)

        //Obtenemos el lenguage actual de la App
        currentLanguage = pantryNutrientModel.getCurrentLanguage()
    }


    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = pantryNutrientModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que devuelve el idioma actual de la App
    fun getCurrentLanguage():String{
        return currentLanguage
    }

    //Metodo que elimina el producto de despensa de la base de datos dado su id
    override fun deletePantry(idPurchase: String) {
        pantryNutrientModel.deletePantry(idPurchase)
    }

    //Metodo que devuelve el grupo de tipos de nutrientes de los productos alimenticios
    override fun getNutrients(language: String) {
        pantryNutrientModel.getNutrients(language).
        observe(pantryNutrientFragment)
        { groupNutrients -> pantryNutrientFragment.onNutrientsLoaded(groupNutrients) }
    }

    //Metodo que devuelve los nutrientes de un alimento de un tiipo determinado
    override fun getNutrientsByType(typeNutrient: String, idFood: String){
        return pantryNutrientModel.getNutrientsByType(typeNutrient, idFood, currentLanguage).
        observe(pantryNutrientFragment)
        { nutrients -> pantryNutrientFragment.onNutrientsTypeLoaded(nutrients) }
    }
}