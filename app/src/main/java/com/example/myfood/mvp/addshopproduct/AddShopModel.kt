package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.mvp.main.MainModel.Companion.db
import com.example.myfood.rest.MySQLREST

class AddShopModel : AddShopContract.Model {
    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    fun getQuantitiesUnit(application: AddShopFragment) {
        val values: LiveData<List<String>> = db.sqliteDao().getQuantitiesUnit()
        values.observe(
            application,
            Observer<List<String>> { application.onQuantitiesLoaded(it) })
    }

    override fun getUserId(application: AddShopFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun insertShop(
        application: AddShopFragment,
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ) {
        MySQLREST.insertShop(application, name, quantity, quantityUnit, userId)
    }

    override fun updateShop(
        application: AddShopFragment,
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ) {
        MySQLREST.updateShop(application, name, quantity, quantityUnit, idShop)
    }

    override fun getShopProduct(
        application: AddShopFragment,
        idShop: String
    ) {
        MySQLREST.getShopProduct(application, idShop)
    }
}