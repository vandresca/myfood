package com.example.myfood.mvp.quantityunit

import android.content.Context
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class QuantityUnitListModel : QuantityUnitListContract.Model {

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

    override fun getQuantityUnits(): List<QuantityUnit> {
        return dbSQLite.sqliteDao().getQuantitiesUnit()
    }

    override fun deleteQuantityUnit(idQuantityUnit: String) {
        dbSQLite.sqliteDao().deleteQuantityUnit(idQuantityUnit)
    }
}