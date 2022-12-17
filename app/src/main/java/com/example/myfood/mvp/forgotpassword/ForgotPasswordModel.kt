package com.example.myfood.mvp.forgotpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class ForgotPasswordModel : ForgotPasswordContract.Model {

    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.YOU_FORGOT_THE_PASSWORD.int)
    }

    override fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.sendLink(language, email)
    }
}