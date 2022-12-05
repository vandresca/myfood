package com.example.myfood.mvp.recipelist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.rest.MySQLREST

class RecipeListModel : RecipeListContract.Model {

    lateinit var dbSQLite: RoomSingleton
    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getUserId(application: RecipeListFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun getRecipeList(application: RecipeListPresenter, language: String) {
        MySQLREST.getRecipeList(application, language)
    }

    override fun getRecipesSuggested(application: RecipeListPresenter, language: String) {
        MySQLREST.getRecipesSuggested(application, language)
    }
}