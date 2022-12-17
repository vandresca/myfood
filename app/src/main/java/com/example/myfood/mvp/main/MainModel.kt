package com.example.myfood.mvp.main

import android.content.Context
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class MainModel : MainContract.Model {
    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(context: Context) {
        dbSQLite = RoomSingleton.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_LIST.int)
    }
}