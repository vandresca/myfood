package com.example.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.PantryListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class PantryListModel : PantryListContract.Model {

    private val myFoodRepository = MyFoodRepository()

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