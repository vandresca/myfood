package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.MyFoodRepository
import com.myfood.databases.databasemysql.entity.NutrientGroupEntity
import com.myfood.databases.databasemysql.entity.NutrientListTypeEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryNutrientModel : PantryNutrientContract.Model {
    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    override fun deletePantry(id: String) {
        myFoodRepository.deletePantry(id)
    }

    override fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity> {
        return myFoodRepository.getNutrients(language)
    }

    override fun getNutrientsByType(typeNutrient: String, idFood: String, language: String):
            MutableLiveData<NutrientListTypeEntity> {
        return myFoodRepository.getNutrientsByType(typeNutrient, idFood, language)
    }
}