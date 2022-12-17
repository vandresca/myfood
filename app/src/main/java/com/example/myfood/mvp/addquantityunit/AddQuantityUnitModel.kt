package com.example.myfood.mvp.addquantityunit

import android.content.Context
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class AddQuantityUnitModel : AddQuantityUnitContract.Model {

    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    override fun addQuantityUnit(quantityUnit: String) {
        myFoodRepository.addQuantityUnit(quantityUnit)
    }

    override fun updateQuantityUnit(quantityUnit: String, id: String) {
        myFoodRepository.updateQuantityUnit(quantityUnit, id)
    }
}