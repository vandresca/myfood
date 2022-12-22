package com.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.LoginEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class LoginModel : LoginContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getLanguages(): List<String> {
        return myFoodRepository.getLanguages()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.LOGIN.int)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun updateCurrentLanguage(language: String) {
        myFoodRepository.updateCurrentLanguage(language)
    }

    override fun updateUserId(userId: String) {
        myFoodRepository.updateUserId(userId)
    }

    override fun login(name: String, password: String): MutableLiveData<LoginEntity> {
        return myFoodRepository.login(name, password)
    }
}
