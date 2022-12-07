package com.example.myfood.mvp.signup

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class SignUpModel : SignUpContract.Model {

    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: Context) {
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

    override fun getCurrentLanguage(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(application) { callback(it) }
    }

    override fun getTranslations(
        application: LifecycleOwner,
        language: Int,
        callback: (List<Translation>) -> Unit
    ) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.SIGN_UP.int)
        values.observe(application) { callback(it) }
    }
}