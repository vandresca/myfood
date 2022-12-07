package com.example.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.rest.MySQLREST

class ConfigModel : ConfigContract.Model {
    lateinit var dbSQLite: RoomSingleton
    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getTranslationsMenu(application: ConfigFragment, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_LIST.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsMenuLoaded(it) })
    }

    override fun getCurrentLanguage(application: LoginActivity) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun getTranslations(application: ConfigFragment, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }

    override fun getLanguages(application: ConfigFragment) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getLanguages()
        values.observe(
            application,
            Observer<List<String>> { application.onLanguagesLoaded(it) })
    }

    override fun getCurrencies(application: ConfigFragment, language: Int) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getCurrencies(language)
        values.observe(
            application,
            Observer<List<String>> { application.onCurrenciesLoaded(it) })
    }

    override fun getUserId(application: ConfigFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun getCurrentLanguage(application: ConfigFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun getCurrentCurrency(application: ConfigFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentCurrency()
        values.observe(
            application,
            Observer<String> { application.onCurrentCurrencyLoaded(it) })
    }

    override fun updateCurrentLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    override fun updateCurrentCurrency(currency: String) {
        dbSQLite.sqliteDao().updateCurrentCurrency(currency)
    }

    override fun changeEmail(application: ConfigFragment, email: String, user: String) {
        MySQLREST.changeEmail(application, email, user)
    }

    override fun getEmail(application: ConfigFragment, user: String) {
        MySQLREST.getEmail(application, user)
    }
}