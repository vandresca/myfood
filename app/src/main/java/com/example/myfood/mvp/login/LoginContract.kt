package com.example.myfood.mvp.login

import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface LoginContract {
    interface View {
        fun onLanguagesLoaded(languages: List<String>)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
        fun updateLanguage(position: Int)
    }

    interface Presenter

    interface Model {
        fun getInstance(application: LoginActivity)
        fun getTranslations(application: LoginActivity, language: Int = LanguageType.ENGLISH.int)
        fun getLanguages(application: LoginActivity)
        fun getCurrentLanguage(application: LoginActivity)
        fun updateCurrencyLanguage(language: String)
        fun updateUserId(userId: String)
        fun login(name: String, password: String, callback: (String?) -> Unit)
    }
}