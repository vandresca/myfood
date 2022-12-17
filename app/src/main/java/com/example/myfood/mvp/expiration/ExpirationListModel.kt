package com.example.myfood.mvp.expiration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.ExpirationListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ExpirationListModel : ExpirationListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getCurrentCurrency(): String {
        return dbSQLite.sqliteDao().getCurrentCurrency()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.EXPIRATION.int)
    }

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun getExpirationList(
        expiration: String,
        idUser: String
    ): MutableLiveData<ExpirationListEntity> {
        val mutable: MutableLiveData<ExpirationListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getExpirationList(expiration, idUser)
                response.body() ?: ExpirationListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).removeExpired(idUser)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }
}