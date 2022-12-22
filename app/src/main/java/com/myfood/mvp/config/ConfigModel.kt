package com.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class ConfigModel : ConfigContract.Model {
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getTranslationsMenu(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    override fun getLanguages(): List<String> {
        return myFoodRepository.getLanguages()
    }

    override fun getCurrencies(language: Int): List<String> {
        return myFoodRepository.getCurrencies(language)
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getCurrentCurrency(): String {
        return myFoodRepository.getCurrentCurrency()
    }

    override fun updateCurrentLanguage(language: String) {
        myFoodRepository.updateCurrentLanguage(language)
    }

    override fun updateCurrentCurrency(currency: String) {
        myFoodRepository.updateCurrentCurrency(currency)
    }

    override fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.changeEmail(email, user)
    }

    override fun getEmail(user: String): MutableLiveData<OneValueEntity> {
        return myFoodRepository.getEmail(user)
    }

    override fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.changePassword(password, user)
    }

    override fun getPassword(user: String): MutableLiveData<OneValueEntity> {
        return myFoodRepository.getPassword(user)
    }
}