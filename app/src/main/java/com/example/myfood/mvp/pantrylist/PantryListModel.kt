package com.example.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.PantryListEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class PantryListModel : PantryListContract.Model {

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
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {
        val mutable: MutableLiveData<PantryListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPantryList(idUser)
                response.body() ?: PantryListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun deletePantry(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).deletePantry(id)
        }
    }
}