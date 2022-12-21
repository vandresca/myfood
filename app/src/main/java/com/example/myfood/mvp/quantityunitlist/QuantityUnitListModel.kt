package com.example.myfood.mvp.quantityunitlist

import android.content.Context
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class QuantityUnitListModel : QuantityUnitListContract.Model {

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

    override fun getQuantityUnits(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    override fun deleteQuantityUnit(idQuantityUnit: String) {
        myFoodRepository.deleteQuantityUnit(idQuantityUnit)
    }
}