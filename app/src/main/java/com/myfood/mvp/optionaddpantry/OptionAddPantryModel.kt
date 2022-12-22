package com.myfood.mvp.optionaddpantry

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class OptionAddPantryModel : OptionAddPantryContract.Model {
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(application: Context) {
        myFoodRepository.getInstance(application)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }
}