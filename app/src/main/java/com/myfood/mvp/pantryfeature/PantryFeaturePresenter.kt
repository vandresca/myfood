package com.myfood.mvp.pantryfeature

import android.content.Context

class PantryFeaturePresenter(
    private val pantryFeatureFragment: PantryFeatureFragment,
    context: Context
) : PantryFeatureContract.Presenter {

    //Declaración de las variables globales
    private val pantryFeatureModel: PantryFeatureModel= PantryFeatureModel()
    private val currentLanguage: String

    init {

        //Creamos las instacias de las bases de datos
        pantryFeatureModel.createInstances(context)

        //Obtenemos el idoma actual de la App
        currentLanguage = pantryFeatureModel.getCurrentLanguage()
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = pantryFeatureModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que obtiene los atributos de un producto de despensa dada su id
    override fun getPantryProduct(idPantry: String){
        pantryFeatureModel.getPantryProduct(idPantry).
        observe(pantryFeatureFragment)
        { product -> pantryFeatureFragment.onLoadPantryFeature(product) }
    }

    //Metodo que elimina un producto de la base de datos dada su id
    override fun deletePantry(idPurchase: String) {
        pantryFeatureModel.deletePantry(idPurchase).
        observe(pantryFeatureFragment)
        {pantryFeatureFragment.onDeletePantryProduct()}
    }

}