package com.myfood.mvp.addstoreplace

import android.content.Context
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class AddStorePlaceModel : AddStorePlaceContract.Model {
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun addStorePlace(storePlace: String) {
        myFoodRepository.addStorePlace(storePlace)
    }

    override fun updateStorePlace(storePlace: String, id: String) {
        myFoodRepository.updateStorePlace(storePlace, id)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }
}