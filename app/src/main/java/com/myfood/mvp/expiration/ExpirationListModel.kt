package com.myfood.mvp.expiration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ExpirationListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class ExpirationListModel : ExpirationListContract.Model {

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