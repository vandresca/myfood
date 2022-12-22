package com.myfood.mvp.quantityunitlist

import android.content.Context
import android.text.Editable
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Unidades de Cantidad
interface QuantityUnitListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun showUpdateQuantityUnitScreen(quantityUnitToUpdate: QuantityUnit)
        fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter)
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
        fun getQuantityUnits(): List<QuantityUnit>
        fun deleteQuantityUnit(idQuantityUnit: String)
    }
}