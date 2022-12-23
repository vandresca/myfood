package com.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.ShopProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Producto Compra
interface AddShopContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta al cargar un producto de compra para  modificarlo
        fun onLoadShopToUpdate(shopProductEntity: ShopProductEntity)

        //Metodo que se ejecuta despues de haber insertado o actualizado correctamente
        //un producto de compra
        fun onInsertedOrUpdatedProduct()
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter: Translatable.Presenter {

        //Metodo que obtiene los atributos de un producto de compra dado su id
        fun getShopProduct(idShop: String)

        //Metodo que inserta un producto de compra para el usuario de la App
        fun insertShop(
            name: String,
            quantity: String,
            quantityUnit: String,
        )

        //Metodo que actualiza un producto de compra en la base de datos dado su id
        fun updateShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        )
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de la base de datos
        fun createInstances(context: Context)

        //Metodo que devuelve los atributos de un producto de compra dado su id
        fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity>

        //Metodo que devuelve las unidades de cantidad de la App
        fun getQuantitiesUnit(): List<QuantityUnit>

        //Metodo que devuelve el id de usario actual de la App
        fun getUserId(): String

        //Metodo que inserta un producto de compra en la base de datos para un usuario
        fun insertShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        ): MutableLiveData<OneValueEntity>

        //Metodo que actualiza un producto de compra en la base de datos dado su id
        fun updateShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        ): MutableLiveData<SimpleResponseEntity>
    }
}