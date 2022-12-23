package com.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Configuración
interface ConfigContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta al obtener el correo del usuario
        fun onGottenEmail(result: OneValueEntity)

        //Metodo que se ejecuta al obtener la contraseña del usuario
        fun onGottenPassword(result: OneValueEntity)

        //Metodo que se ejecuta tras cambiar el correo del usuario
        fun onChangeEmail(result: SimpleResponseEntity)

        //Metodo que se ejecuta tras cambiar la contraseña del usuario
        fun onChangePassword(result: SimpleResponseEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que obtiene todos los idiomas de la App
        fun getLanguages(): List<String>

        //Metodo que actualiza el lenguage actual de la App
        fun updateCurrentLanguage(language: String)

        //Metodo que actualiza el tipo de moneda actual de la App
        fun updateCurrentCurrency(currency: String)

        //Metodo que obtiene las traducciones del menu de navegación
        fun getTranslationsMenu(): MutableMap<String, String>

        //Metodo que obtiene el correo electronico del usuario
        fun getEmail()

        //Metodo que obtiene la contraseña del usuario
        fun getPassword()

        //Metodo que cambia el correo electronico del usuario
        fun changeEmail(email: String)

        //Metodo que cambia la contraseña del usuario
        fun changePassword(password: String)

    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene las traducciones del menu de navegación
        fun getTranslationsMenu(language: Int): List<Translation>

        //Metodo que obtiene los idiomas de la App
        fun getLanguages(): List<String>

        //Metodo que obtiene los tipos de moneda del lenguage actual de la App
        fun getCurrencies(language: Int): List<String>

        //Metodo que obtiene el id de usuario actual de la App
        fun getUserId(): String

        //Metodo que obtiene el tipo de moneda actual de la App
        fun getCurrentCurrency(): String

        //Metodo que actualiza el lenguage actual de la App
        fun updateCurrentLanguage(language: String)

        //Metodo que actualiza el tipo de moneda actual de la App
        fun updateCurrentCurrency(currency: String)

        //Metodo que cambia el correo electronico del usuario
        fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity>

        //Metodo que obtiene el correo electronico del usuario
        fun getEmail(user: String): MutableLiveData<OneValueEntity>

        //Netodo que cambia la contraseña del usuario
        fun changePassword(password: String, user: String): MutableLiveData<SimpleResponseEntity>

        //Metodo que obtiene la contrasña del usuario
        fun getPassword(user: String): MutableLiveData<OneValueEntity>
    }
}