package com.example.myfood.mvp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class LoginModel : LoginContract.Model {

    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: LoginActivity) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getTranslations(application: LoginActivity, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.LOGIN.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }

    override fun getLanguages(application: LoginActivity) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getLanguages()
        values.observe(
            application,
            Observer<List<String>> { application.onLanguagesLoaded(it) })
    }

    override fun getCurrentLanguage(application: LoginActivity) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun updateCurrencyLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    override fun updateUserId(userId: String) {
        dbSQLite.sqliteDao().updateUserId(userId)
    }

    override fun login(name: String, password: String, callback: (String?) -> Unit) {
        return MySQLREST.login(name, password, callback)
    }
}