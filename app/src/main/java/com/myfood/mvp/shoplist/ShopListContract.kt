package com.myfood.mvp.shoplist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ShopListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Compra
interface ShopListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta al hacer click en el boton modificar del item de la
        //lista de productos de compra
        fun onUpdateShopProduct(idShop: String)

        //Metodo que inicializa el recyclerview
        fun initRecyclerView(shopListAdapter: ShopListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que se ejecuta una vez que tenemos los productos de compra de la base
        //de datos
        fun loadData(shopListEntity: ShopListEntity)

        //Metodo que se ejecuta cuando se produce un cambio de contenido de texto en el
        //campo de texto del buscador
        fun doFilter(userFilter: Editable?)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene el id de usuario actual de la App
        fun getUserId(): String

        //Metodo que obtiene la lista de productos de compra del usuario actual
        fun getShopList(idUser: String): MutableLiveData<ShopListEntity>

        //Metodo que elimina el producto de compra seleccionado de la base de datos
        //dado su id
        fun deleteShop(idShop: String): MutableLiveData<SimpleResponseEntity>
    }
}