package com.example.myfood.mvp.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class SignUpModel : SignUpContract.Model {

    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: SignUpActivity) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String,
        callback: (String?) -> Unit
    ) {
        return MySQLREST.insertUser(name, surnames, email, password, callback)
    }

    override fun getTranslations(application: SignUpActivity, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.LOGIN.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }
}