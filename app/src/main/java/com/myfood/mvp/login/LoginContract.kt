package com.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.LoginEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Login
interface LoginContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun updateLanguage(position: Int)
        fun login()
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun login(name: String, password: String): MutableLiveData<LoginEntity>
        fun getLanguages(): List<String>
        fun updateCurrentLanguage(language: String)
        fun updateUserId(userId: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun login(name: String, password: String): MutableLiveData<LoginEntity>
        fun getLanguages(): List<String>
        fun updateCurrentLanguage(language: String)
        fun updateUserId(userId: String)
    }
}