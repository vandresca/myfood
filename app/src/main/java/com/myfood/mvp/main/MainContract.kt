package com.myfood.mvp.main

import android.content.Context
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para el menu
// de navegación
interface MainContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View{
        fun setTranslationsMenu()
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter{
        fun getTranslationsMenu():MutableMap<String, String>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)
    }
}