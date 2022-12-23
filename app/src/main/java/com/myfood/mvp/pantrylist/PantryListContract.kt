package com.myfood.mvp.pantrylist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Despensa
interface PantryListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta al hacer clic en el boton modificar de un item de
        //la lista
        fun onUpdatePantry(idPurchase: String)

        //Metodo que se ejecuta al hacer clic sobre un elemento de la lista
        fun onClickPantryElement(idPantry: String)

        //Metodo que inicializa el recyclerview de la lista de despensa
        fun initRecyclerView(purchaseAdapter: PantryListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que se ejecuta una vez que tenemos los elementos de la lista de
        //productos de despensa
        fun loadData(pantryListEntity: PantryListEntity)

        //Metodo que se ejecuta tras modificar el contenido del campo de texto del buscador
        fun doFilter(userFilter: Editable?)

        //Metodo que obtiene el tipo de moneda actual de la App
        fun getCurrentCurrency(): String
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene la lista de productos de despensa del usuario actual
        fun getPantryList(idUser: String): MutableLiveData<PantryListEntity>

        //Metodo que obtiene el tipo de moneda actual de la App
        fun getCurrentCurrency(): String

        //Metodo que elimina un producto de despensa de la base de datos dado su id
        fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity>

        //Metodo que obtiene el id de usuario actual de la App
        fun getUserId(): String
    }
}