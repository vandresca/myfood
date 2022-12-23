package com.myfood.mvp.recipe

import android.content.Context


class RecipePresenter(
    private val recipeFragment: RecipeFragment,
    context: Context
) : RecipeContract.Presenter {

    //Declaramos las variables globales
    private val recipeModel: RecipeModel = RecipeModel()
    private val currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        recipeModel.createInstances(context)

        //Obtenemos el idioma actual de la App
        currentLanguage = recipeModel.getCurrentLanguage()
    }

    //Metodo que obtiene los atributos de la receta en un idioma dada su id
    override fun getRecipe(idRecipe: String, idLanguage: String) {
        return recipeModel.getRecipe(idRecipe, idLanguage).
        observe(recipeFragment)
        { recipe -> recipeFragment.onRecipeLoaded(recipe) }
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = recipeModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }
}
