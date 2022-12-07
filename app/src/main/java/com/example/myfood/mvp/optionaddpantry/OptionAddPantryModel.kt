package com.example.myfood.mvp.optionaddpantry

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class OptionAddPantryModel : OptionAddPantryContract.Model {
    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getCurrentLanguage(application: OptionAddPantryFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(
            application,
            Observer<String> { application.onCurrentLanguageLoaded(it) })
    }

    override fun getTranslations(application: OptionAddPantryFragment, language: Int) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
        values.observe(
            application,
            Observer<List<Translation>> { application.onTranslationsLoaded(it) })
    }
}