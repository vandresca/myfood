package com.example.myfood.mvp.quantityunit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class QuantityUnitListModel : QuantityUnitListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }


    override fun getQuantityUnitList(application: QuantityUnitListFragment) {
        val values: LiveData<List<QuantityUnit>> = dbSQLite.sqliteDao().getQuantitiesUnit()
        values.observe(
            application,
            Observer<List<QuantityUnit>> { application.loadData(it) })
    }

    override fun deleteQuantityUnit(idQuantityUnit: String) {
        //MySQLREST.deleteShop(idQuantityUnit)
    }

    override fun updateQuantityUnit(idQuantityUnit: String) {
        TODO("Not yet implemented")
    }

    override fun addQuantityUnit(quantityUnit: String) {
        TODO("Not yet implemented")
    }

    override fun getCurrentLanguage(application: QuantityUnitListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun getTranslations(application: QuantityUnitListFragment, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }

}