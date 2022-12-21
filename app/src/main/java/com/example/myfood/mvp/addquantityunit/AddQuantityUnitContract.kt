package com.example.myfood.mvp.addquantityunit

import android.content.Context
import com.example.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Unidad de Cantidad
interface AddQuantityUnitContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun addQuantityUnit(quantityUnit: String)
        fun updateQuantityUnit(quantityUnit: String, idQuantityUnit: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun addQuantityUnit(quantityUnit: String)
        fun updateQuantityUnit(quantityUnit: String, id: String)
    }
}