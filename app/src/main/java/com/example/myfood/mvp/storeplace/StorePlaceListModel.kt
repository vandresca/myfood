package com.example.myfood.mvp.storeplace

import android.content.Context
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class StorePlaceListModel : StorePlaceListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
    }

    override fun getStorePlaces(): List<StorePlace> {
        return dbSQLite.sqliteDao().getStorePlaces()
    }

    override fun deleteStorePlace(idPlace: String) {
        dbSQLite.sqliteDao().deleteStorePlace(idPlace)
    }
}