package com.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryFeatureModel : PantryFeatureContract.Model {
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return myFoodRepository.getPantryProduct(idPantry)
    }

    override fun deletePantry(id: String) {
        myFoodRepository.deletePantry(id)
    }
}