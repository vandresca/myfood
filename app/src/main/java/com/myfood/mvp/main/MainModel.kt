package com.myfood.mvp.main

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class MainModel : MainContract.Model {
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }
}