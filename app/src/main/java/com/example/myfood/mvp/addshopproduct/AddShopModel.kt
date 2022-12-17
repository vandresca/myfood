package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.ShopProductEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class AddShopModel : AddShopContract.Model {
    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.SHOPPING_LIST.int)
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return dbSQLite.sqliteDao().getQuantitiesUnit()
    }

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {
        val mutable: MutableLiveData<ShopProductEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getShopProduct(idShop)
                response.body() ?: ShopProductEntity(
                    "KO",
                    "", "", ""
                )
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java)
                .insertShop(name, quantity, quantityUnit, userId)
        }
    }

    override fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).updateShop(name, quantity, quantityUnit, idShop)
        }
    }
}