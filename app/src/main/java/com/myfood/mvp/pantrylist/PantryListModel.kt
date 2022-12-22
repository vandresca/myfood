package com.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryListModel : PantryListContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getCurrentCurrency(): String {
        return myFoodRepository.getCurrentCurrency()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {
        return myFoodRepository.getPantryList(idUser)
    }

    override fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.deletePantry(id)
    }
}