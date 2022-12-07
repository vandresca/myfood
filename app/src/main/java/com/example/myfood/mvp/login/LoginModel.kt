package com.example.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class LoginModel : LoginContract.Model {

    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getTranslations(
        application: LifecycleOwner,
        language: Int,
        callback: (List<Translation>) -> Unit
    ) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.LOGIN.int)
        values.observe(application) { callback(it) }
    }

    override fun getLanguages(application: LifecycleOwner, callback: (List<String>) -> Unit) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getLanguages()
        values.observe(application) { callback(it) }
    }

    override fun getCurrentLanguage(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(application) { callback(it) }
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