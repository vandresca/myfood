package com.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasesqlite.entity.Translation

class PantryFeaturePresenter(
    private val pantryFeatureView: PantryFeatureContract.View,
    private val pantryFeatureModel: PantryFeatureContract.Model,
    context: Context
) : PantryFeatureContract.Presenter {
    init {
        pantryFeatureModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return pantryFeatureModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = pantryFeatureModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return pantryFeatureModel.getPantryProduct(idPantry)
    }

    override fun deletePantry(idPurchase: String) {
        pantryFeatureModel.deletePantry(idPurchase)
    }

}