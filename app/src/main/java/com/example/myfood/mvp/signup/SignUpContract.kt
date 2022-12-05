package com.example.myfood.mvp.signup

import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface SignUpContract {
    interface View {
        fun onLanguagesLoaded(languages: List<String>)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
        fun updateLanguage(position: Int)
        fun onLogged(response: String?)

    }

    interface Presenter

    interface Model {
        fun getInstance(application: SignUpActivity)
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String,
            callback: (String?) -> Unit
        )

        fun getTranslations(application: SignUpActivity, language: Int = LanguageType.ENGLISH.int)
    }
}