package com.myfood.mvp.signup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class SignUpModel : SignUpContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.SIGN_UP.int)
    }

    override fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String,
    ): MutableLiveData<OneValueEntity> {
        return myFoodRepository.insertUser(name, surnames, email, password)
    }
}