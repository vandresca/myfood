package com.example.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class PantryListModel : PantryListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getPantryList(idUser: String, callback: (String?) -> Unit) {
        MySQLREST.getPantryList(idUser, callback)
    }

    override fun deletePantry(id: String) {
        MySQLREST.deletePantry(id)
    }

    override fun getUserId(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(application) { callback(it) }
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
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_LIST.int)
        values.observe(application) { callback(it) }
    }
}