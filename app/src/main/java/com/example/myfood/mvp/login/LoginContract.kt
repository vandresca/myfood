package com.example.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.myfood.interfaces.Translatable

interface LoginContract {
    interface View : Translatable.View {
        fun onLanguagesLoaded(languages: List<String>)
        fun updateLanguage(position: Int)
        fun onLogged(result: String?)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun getLanguages(application: LifecycleOwner, callback: (List<String>) -> Unit)
        fun updateCurrencyLanguage(language: String)
        fun updateUserId(userId: String)
        fun login(name: String, password: String, callback: (String?) -> Unit)
    }
}