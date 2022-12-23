package com.myfood.mvp.addquantityunit

import android.content.Context
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Unidad de Cantidad
interface AddQuantityUnitContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter: Translatable.Presenter {

        //Metodo que añade una unidad de cantidad a la base de datos SQLite
        fun addQuantityUnit(quantityUnit: String)

        //Metodo que actualiza una unidad de cantidad en la base de datos SQLite
        fun updateQuantityUnit(quantityUnit: String, idQuantityUnit: String)

    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que instancia las bases de datos
        fun createInstances(context: Context)

        //Metodo que añade una unidad de cantidad a la base de datos SQLite
        fun addQuantityUnit(quantityUnit: String)

        //Metodo que actualiza una unidad de cantidad dada su id en la base de datos
        //SQLite
        fun updateQuantityUnit(quantityUnit: String, id: String)
    }
}