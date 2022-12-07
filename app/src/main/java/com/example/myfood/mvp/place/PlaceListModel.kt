package com.example.myfood.mvp.place

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class PlaceListModel : PlaceListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }


    override fun getPlaceList(application: PlaceListFragment) {
        val values: LiveData<List<StorePlace>> = dbSQLite.sqliteDao().getStorePlaces()
        values.observe(
            application,
            Observer<List<StorePlace>> { application.loadData(it) })
    }

    override fun deleteShop(idPlace: String) {
        MySQLREST.deleteShop(idPlace)
    }

    override fun updatePlace(idPlace: String) {
        TODO("Not yet implemented")
    }

    override fun addPlace(place: String) {
        TODO("Not yet implemented")
    }

    override fun getCurrentLanguage(application: PlaceListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun getTranslations(application: PlaceListFragment, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }

}