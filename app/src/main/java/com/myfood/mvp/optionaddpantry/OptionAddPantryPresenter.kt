package com.myfood.mvp.optionaddpantry

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation


class OptionAddPantryPresenter(
    private val optionAddPantryView: OptionAddPantryContract.View,
    private val optionAddPantryModel: OptionAddPantryContract.Model,
    context: Context
) : OptionAddPantryContract.Presenter {
    init {
        optionAddPantryModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return optionAddPantryModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = optionAddPantryModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }
}