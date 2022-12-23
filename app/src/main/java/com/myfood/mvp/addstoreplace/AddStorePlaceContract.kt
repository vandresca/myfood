package com.myfood.mvp.addstoreplace

import android.content.Context
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Lugar Almacenaje
interface AddStorePlaceContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter: Translatable.Presenter {

        //Metodo que añade un lugar de almacenaje en la base de datos SQLite
        fun addStorePlace(storePlace: String)

        //Metodo que actualiza un lugar de almacenaje en la base de datos SQLite
        fun updateStorePlace(storePlace: String, idStorePlace: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de la bases de datos
        fun createInstances(context: Context)

        //Metodo que añade un lugar de almacenaje en la base de datos SQLite
        fun addStorePlace(storePlace: String)

        //Metodo que actualiza un lugar de almacenaje en la base de datos SQLite
        fun updateStorePlace(storePlace: String, id: String)
    }
}