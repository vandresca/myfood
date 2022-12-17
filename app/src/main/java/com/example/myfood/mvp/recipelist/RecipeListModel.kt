package com.example.myfood.mvp.recipelist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.RecipeListEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RecipeListModel : RecipeListContract.Model {

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
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.RECIPES.int)
    }

    override fun getRecipeList(language: String): MutableLiveData<RecipeListEntity> {
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipeList(language)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun getRecipesSuggested(language: String): MutableLiveData<RecipeListEntity> {
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipesSuggested(language)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }
}