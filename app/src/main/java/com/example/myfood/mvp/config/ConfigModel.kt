package com.example.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ConfigModel : ConfigContract.Model {
    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getTranslationsMenu(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.CONFIG.int)
    }

    override fun getLanguages(): List<String> {
        return dbSQLite.sqliteDao().getLanguages()
    }

    override fun getCurrencies(language: Int): List<String> {
        return dbSQLite.sqliteDao().getCurrencies(language)
    }

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun getCurrentCurrency(): String {
        return dbSQLite.sqliteDao().getCurrentCurrency()
    }

    override fun updateCurrentLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    override fun updateCurrentCurrency(currency: String) {
        dbSQLite.sqliteDao().updateCurrentCurrency(currency)
    }


    override fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changeEmail(email, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun getEmail(user: String): MutableLiveData<OneValueEntity> {
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getEmail(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changePassword(password, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun getPassword(user: String): MutableLiveData<OneValueEntity> {
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPassword(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }
}