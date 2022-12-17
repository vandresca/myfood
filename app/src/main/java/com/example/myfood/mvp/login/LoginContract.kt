package com.example.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.LoginEntity

interface LoginContract {
    interface View : Translatable.View {
        fun updateLanguage(position: Int)
        fun login()
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun login(name: String, password: String): MutableLiveData<LoginEntity>
        fun getLanguages(): List<String>
        fun updateCurrentLanguage(language: String)
        fun updateUserId(userId: String)
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun login(name: String, password: String): MutableLiveData<LoginEntity>
        fun getLanguages(): List<String>
        fun updateCurrentLanguage(language: String)
        fun updateUserId(userId: String)
    }
}