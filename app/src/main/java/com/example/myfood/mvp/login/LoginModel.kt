package com.example.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.LoginEntity


class LoginModel : LoginContract.Model {

    private val myFoodRepository = MyFoodRepository()

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
