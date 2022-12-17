package com.example.myfood.mvp.expiration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.ExpirationListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class ExpirationListModel : ExpirationListContract.Model {

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
        return myFoodRepository.getTranslations(language, ScreenType.EXPIRATION.int)
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getExpirationList(
        expiration: String,
        idUser: String
    ): MutableLiveData<ExpirationListEntity> {
        return myFoodRepository.getExpirationList(expiration, idUser)
    }

    override fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.removeExpired(idUser)
    }
}