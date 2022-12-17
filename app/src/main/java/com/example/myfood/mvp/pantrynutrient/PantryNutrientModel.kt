package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class PantryNutrientModel : PantryNutrientContract.Model {
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
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    override fun deletePantry(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).deletePantry(id)
        }
    }

    override fun getNutrients(): MutableLiveData<NutrientGroupEntity> {
        val mutable: MutableLiveData<NutrientGroupEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getNutrients()
                response.body() ?: NutrientGroupEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun getNutrientsByType(typeNutrient: String, idFood: String):
            MutableLiveData<NutrientListTypeEntity> {
        val mutable: MutableLiveData<NutrientListTypeEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getNutrientsByType(typeNutrient, idFood)
                response.body() ?: NutrientListTypeEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }
}