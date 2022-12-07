package com.example.myfood.mvp.forgotpassword

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class ForgotPasswordModel : ForgotPasswordContract.Model {

    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: ForgotPasswordActivity) {
        dbSQLite = RoomSingleton.getInstance(application)
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
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.YOU_FORGOT_THE_PASSWORD.int)
        values.observe(application) { callback(it) }
    }
}