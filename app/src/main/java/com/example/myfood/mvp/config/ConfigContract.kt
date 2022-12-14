package com.example.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Configuración
interface ConfigContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onGottenEmail(result: OneValueEntity)
        fun onGottenPassword(result: OneValueEntity)
        fun onChangeEmail(result: SimpleResponseEntity)
        fun onChangePassword(result: SimpleResponseEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun getLanguages(): List<String>
        fun getUserId(): String
        fun updateCurrentLanguage(language: String)
        fun updateCurrentCurrency(currency: String)
        fun getCurrentCurrency(): String
        fun getTranslationsMenu(language: Int): MutableMap<String, Translation>
        fun getCurrencies(language: Int): List<String>
        fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity>
        fun getEmail(user: String): MutableLiveData<OneValueEntity>
        fun changePassword(password: String, user: String): MutableLiveData<SimpleResponseEntity>
        fun getPassword(user: String): MutableLiveData<OneValueEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getTranslationsMenu(language: Int): List<Translation>
        fun getLanguages(): List<String>
        fun getCurrencies(language: Int): List<String>
        fun getUserId(): String
        fun getCurrentCurrency(): String
        fun updateCurrentLanguage(language: String)
        fun updateCurrentCurrency(currency: String)
        fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity>
        fun getEmail(user: String): MutableLiveData<OneValueEntity>
        fun changePassword(password: String, user: String): MutableLiveData<SimpleResponseEntity>
        fun getPassword(user: String): MutableLiveData<OneValueEntity>
    }
}