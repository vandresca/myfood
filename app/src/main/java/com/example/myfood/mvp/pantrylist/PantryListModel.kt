package com.example.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.rest.MySQLREST

class PantryListModel : PantryListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getPantryList(application: PantryListPresenter, idUser: String) {
        MySQLREST.getPantryList(application, idUser)
    }

    override fun deletePantry(id: String) {
        MySQLREST.deletePantry(id)
    }

    override fun getUserId(application: PantryListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }
}