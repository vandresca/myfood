package com.myfood.mvp.addquantityunit

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation


class AddQuantityUnitPresenter(
    private val addQuantityUnitView: AddQuantityUnitContract.View,
    private val addQuantityUnitModel: AddQuantityUnitContract.Model,
    context: Context
) : AddQuantityUnitContract.Presenter {
    init {
        addQuantityUnitModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return addQuantityUnitModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = addQuantityUnitModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun addQuantityUnit(quantityUnit: String) {
        addQuantityUnitModel.addQuantityUnit(quantityUnit)
    }

    override fun updateQuantityUnit(quantityUnit: String, idQuantityUnit: String) {
        addQuantityUnitModel.updateQuantityUnit(quantityUnit, idQuantityUnit)
    }
}