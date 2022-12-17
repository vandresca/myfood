package com.example.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.ShopListEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ShopListModel : ShopListContract.Model {

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

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {
        val mutable: MutableLiveData<ShopListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getShopList(idUser)
                response.body() ?: ShopListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun deleteShop(idShop: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).deleteShop(idShop)
        }
    }

}