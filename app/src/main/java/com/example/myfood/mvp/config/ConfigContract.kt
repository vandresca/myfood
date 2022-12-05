package com.example.myfood.mvp.config

import android.content.Context
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface ConfigContract {
    interface View {
        fun onLanguagesLoaded(languages: List<String>)
        fun onCurrenciesLoaded(currencies: List<String>)
        fun onGottenEmail(response: String?)
        fun onChangeEmail(response: String?)
        fun onUserIdLoaded(idUser: String)
        fun onCurrentLanguageLoaded(language: String)
        fun onCurrentCurrencyLoaded(currency: String)
        fun onTranslationsMenuLoaded(translations: List<Translation>)
        fun onTranslationsLoaded(translations: List<Translation>)
    }

    interface Presenter

    interface Model {
        fun getInstance(application: Context)
        fun getTranslationsMenu(
            application: ConfigFragment,
            language: Int = LanguageType.ENGLISH.int
        )

        fun getTranslations(application: ConfigFragment, language: Int = LanguageType.ENGLISH.int)
        fun getLanguages(application: ConfigFragment)
        fun getCurrencies(application: ConfigFragment, language: Int)
        fun getUserId(application: ConfigFragment)
        fun changeEmail(application: ConfigFragment, email: String, user: String)
        fun getEmail(application: ConfigFragment, user: String)
        fun getCurrentLanguage(application: ConfigFragment)
        fun getCurrentCurrency(application: ConfigFragment)
        fun updateCurrentLanguage(language: String)
        fun updateCurrentCurrency(currency: String)
    }
}