package com.myfood.mvp.storeplacelist

import android.content.Context
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class StorePlaceListModel : StorePlaceListContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    override fun getStorePlaces(): List<StorePlace> {
        return myFoodRepository.getStorePlaces()
    }

    override fun deleteStorePlace(idPlace: String) {
        myFoodRepository.deleteStorePlace(idPlace)
    }
}