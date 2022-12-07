package com.example.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class ShopListModel : ShopListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getUserId(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(application) { callback(it) }
    }

    override fun getShopList(idUser: String, callback: (String?) -> Unit) {
        MySQLREST.getShopList(idUser) { response -> callback(response) }
    }

    override fun deleteShop(idShop: String) {
        MySQLREST.deleteShop(idShop)
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

}