package com.myfood.mvp.expiration

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ExpirationListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Caducidad
interface ExpirationListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que inicia el recyclerview
        fun initRecyclerView(expirationListAdapter: ExpirationListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que carga la lista de productos en el recyclerview
        fun loadList(expirationListEntity: ExpirationListEntity)

        //Metodo que realiza el filtrado del buscador
        fun doFilter(userFilter: Editable?)

        //Metodo que se ejecuta despues de eliminar los productos caducados
        fun onRemovedExpired(result: SimpleResponseEntity)

        //Metodo que obtiene el tipo de moneda actual de la App
        fun getCurrentCurrency(): String
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene el id de usuario actual de la App
        fun getUserId(): String

        //Metodo que obtiene el tipo de moneda actual de la App
        fun getCurrentCurrency(): String

        //Metodo que obtiene la lista de productos de despensa segun
        //la caducidad especificada
        fun getExpirationList(
            expiration: String,
            idUser: String
        ): MutableLiveData<ExpirationListEntity>

        //Metodo que elimina los productos caducados del usuario
        fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity>
    }
}