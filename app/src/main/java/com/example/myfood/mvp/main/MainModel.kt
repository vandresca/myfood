package com.example.myfood.mvp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType
import com.example.myfood.enum.ScreenType

class MainModel {
    companion object {
        lateinit var db: RoomSingleton
        fun getInstance(application: MainActivity) {
            db = RoomSingleton.getInstance(application)
        }

        fun getTranslations(application: MainActivity, language: Int = LanguageType.ENGLISH.int) {
            val values: LiveData<List<Translation>> =
                db.sqliteDao().getTranslations(language, ScreenType.PURCHASE_LIST.int)
            values.observe(
                application,
                Observer<List<Translation>> { application.onTranslationsLoaded(it) })
        }

        fun getCurrentLanguage(application: MainActivity) {
            val values: LiveData<String> = db.sqliteDao().getCurrentLanguage()
            values.observe(
                application,
                Observer<String> { application.onCurrentLanguageLoaded(it) })
        }

    }
}