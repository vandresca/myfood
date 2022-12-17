package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity

class PantryNutrientPresenter(
    private val pantryNutrientView: PantryNutrientContract.View,
    private val pantryNutrientModel: PantryNutrientContract.Model,
    context: Context
) : PantryNutrientContract.Presenter {
    init {
        pantryNutrientModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return pantryNutrientModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = pantryNutrientModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun deletePantry(idPurchase: String) {
        pantryNutrientModel.deletePantry(idPurchase)
    }

    override fun getNutrients(): MutableLiveData<NutrientGroupEntity> {
        return pantryNutrientModel.getNutrients()
    }

    override fun getNutrientsByType(typeNutrient: String, idFood: String):
            MutableLiveData<NutrientListTypeEntity> {
        return pantryNutrientModel.getNutrientsByType(typeNutrient, idFood)
    }
}