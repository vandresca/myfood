package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class AddShopModel : AddShopContract.Model {
    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
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
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.SHOPPING_LIST.int)
        values.observe(application) { callback(it) }
    }

    override fun getQuantitiesUnit(
        application: LifecycleOwner,
        callback: (List<QuantityUnit>) -> Unit
    ) {
        val values: LiveData<List<QuantityUnit>> = dbSQLite.sqliteDao().getQuantitiesUnit()
        values.observe(application) { callback(it) }
    }

    override fun getUserId(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(application) { callback(it) }
    }

    override fun getShopProduct(
        idShop: String,
        callback: (String?) -> Unit
    ) {
        MySQLREST.getShopProduct(idShop, callback)
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
}