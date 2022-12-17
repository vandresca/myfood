package com.example.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.LoginEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit


class LoginModel : LoginContract.Model {

    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getLanguages(): List<String> {
        return dbSQLite.sqliteDao().getLanguages()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.LOGIN.int)
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun updateCurrentLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    override fun updateUserId(userId: String) {
        dbSQLite.sqliteDao().updateUserId(userId)
    }

    override fun login(name: String, password: String): MutableLiveData<LoginEntity> {
        val mutable: MutableLiveData<LoginEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).login(name, password)
                response.body() ?: LoginEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }
}
