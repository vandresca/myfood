package com.example.myfood.mvp.expiration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.rest.MySQLREST

class ExpirationListModel : ExpirationListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getUserId(application: ExpirationListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun getExpirationList(
        application: ExpirationListPresenter,
        expiration: String,
        idUser: String
    ) {
        MySQLREST.getExpirationList(application, expiration, idUser)
    }

    override fun removeExpired(application: ExpirationListPresenter, idUser: String) {
        MySQLREST.removeExpired(application, idUser)
    }

}