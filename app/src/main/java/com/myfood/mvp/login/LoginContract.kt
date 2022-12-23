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

        //Metodo que se ejecuta tras chequear login con un usuario y contraseña
        fun onLogin(result: LoginEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que chequea login con un usuario y contraseña
        fun login(name: String, password: String)

        //Metodo que actualiza el id de usuario actual de la App
        fun updateUserId(userId: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de la base de datos
        fun createInstances(context: Context)

        //Metodo que chequea el login de un nombre y contraseña
        fun login(name: String, password: String): MutableLiveData<LoginEntity>

        //Metodo que obtiene los idiomas disponibles en la App
        fun getLanguages(): List<String>

        //Metodo que actualiza el lenguaje actual de la App
        fun updateCurrentLanguage(language: String)

        //Metodo que actualiza el id de usuario actual de la App
        fun updateUserId(userId: String)
    }
}