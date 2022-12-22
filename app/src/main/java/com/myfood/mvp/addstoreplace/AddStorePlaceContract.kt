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
    interface Presenter : Translatable.Presenter {
        fun addStorePlace(storePlace: String)
        fun updateStorePlace(storePlace: String, idStorePlace: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun addStorePlace(storePlace: String)
        fun updateStorePlace(storePlace: String, id: String)
    }
}