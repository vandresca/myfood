package com.example.myfood.mvp.main

import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.mvp.optionaddpantry.OptionAddPantryContract

class MainPresenter(
    private val mainView: MainContract.View,
    private val mainModel: MainContract.Model,
    mainActivity: MainActivity
) : OptionAddPantryContract.Presenter {
    init {
        mainModel.getInstance(mainActivity)
    }

    override fun getCurrentLanguage(): String {
        return mainModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = mainModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }
}