package com.example.myfood.mvp.forgotpassword

import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ForgotPasswordModel : ForgotPasswordContract.Model {

    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: ForgotPasswordActivity) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao()
            .getTranslations(language, ScreenType.YOU_FORGOT_THE_PASSWORD.int)
    }

    override fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).sendLink(language, email)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }
}