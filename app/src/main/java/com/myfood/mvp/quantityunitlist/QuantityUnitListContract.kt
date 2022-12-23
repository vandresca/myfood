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

        //Metodo que se ejecuta cuando se clica en el boton modificar de un item de la
        //lista
        fun onUpdateQuantityUnit(quantityUnitToUpdate: QuantityUnit)

        //Metodo que inicializa el recyclerview
        fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que se ejecuta cuando se modifica el campo de texto del buscador
        //para buscar un elemento
        fun doFilter(userFilter: Editable?)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene la lista de unidades de cantidad de la App
        fun getQuantityUnits(): List<QuantityUnit>

        //Metodo que elimina una unidad de cantidad de la base de datos SQLite
        //dada una id
        fun deleteQuantityUnit(idQuantityUnit: String)
    }
}