package com.example.myfood.mvp.main

import android.content.Context
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.interfaces.Translatable

interface MainContract {
    interface View : Translatable.View {
        fun setTranslations(translations: MutableMap<String, Translation>?)
    }

    interface Presenter : Translatable.Presenter
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
    }
}