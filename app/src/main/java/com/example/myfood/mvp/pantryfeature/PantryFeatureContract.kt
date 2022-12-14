package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.PantryProductEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Característica Producto Despensa
interface PantryFeatureContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun deletePantry(idPurchase: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun deletePantry(id: String)
    }
}