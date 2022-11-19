package com.example.myfood.mvp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType
import com.example.myfood.enum.ScreenType

class LoginModel {
    companion object {
        lateinit var db: RoomSingleton
        fun getInstance(application: LoginActivity) {
            db = RoomSingleton.getInstance(application)
        }

        fun getTranslations(application: LoginActivity, language: Int = LanguageType.ENGLISH.int) {
            val values: LiveData<List<Translation>> =
                db.sqliteDao().getTranslations(language, ScreenType.LOGIN.int)
            values.observe(
                application,
                Observer<List<Translation>> { application.onTranslationsLoaded(it) })
        }

        fun getLanguages(application: LoginActivity) {
            val values: LiveData<List<String>> = db.sqliteDao().getLanguages()
            values.observe(
                application,
                Observer<List<String>> { application.onLanguagesLoaded(it) })
        }
    }
}