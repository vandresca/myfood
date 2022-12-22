package com.myfood.mvp.storeplacelist

import android.content.Context
import android.text.Editable
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Lugares de Almacenaje
interface StorePlaceListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun showUpdateStorePlaceScreen(storePlaceToUpdate: StorePlace)
        fun initRecyclerView(storePlacesAdapter: StorePlaceListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun loadData()
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getStorePlaces(): List<StorePlace>
        fun deleteStorePlace(idPlace: String)
    }
}