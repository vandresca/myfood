package com.example.myfood.mvp.addplace

import android.content.Context
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class AddStorePlaceModel : AddStorePlaceContract.Model {
    lateinit var dbSQLite: RoomSingleton
    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun addStorePLace(storePlace: String) {
        dbSQLite.sqliteDao().addStorePlace(storePlace)
    }

    override fun updateStorePlace(storePlace: String, id: String) {
        dbSQLite.sqliteDao().updateStorePlace(storePlace, id)
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
    }
}