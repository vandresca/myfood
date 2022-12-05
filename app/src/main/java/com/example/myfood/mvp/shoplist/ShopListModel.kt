package com.example.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.rest.MySQLREST

class ShopListModel : ShopListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getUserId(application: ShopListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun getShopList(application: ShopListPresenter, idUser: String) {
        MySQLREST.getShopList(application, idUser)
    }

    override fun deleteShop(idShop: String) {
        MySQLREST.deleteShop(idShop)
    }

}