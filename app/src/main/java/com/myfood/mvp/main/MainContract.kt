package com.myfood.mvp.main

import android.content.Context
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para el menu
// de navegación
interface MainContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
    }
}