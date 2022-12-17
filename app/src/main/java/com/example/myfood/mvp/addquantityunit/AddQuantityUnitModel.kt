package com.example.myfood.mvp.addquantityunit

import android.content.Context
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class AddQuantityUnitModel : AddQuantityUnitContract.Model {
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

    override fun addQuantityUnit(quantityUnit: String) {
        dbSQLite.sqliteDao().addQuantityUnit(quantityUnit)
    }

    override fun updateQuantityUnit(quantityUnit: String, id: String) {
        dbSQLite.sqliteDao().updateQuantityUnit(quantityUnit, id)
    }
}