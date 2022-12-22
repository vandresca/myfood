package com.myfood.mvp.addpantryproduct

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.OpenFoodEntity
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Producto Despensa
interface AddPantryContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta tras el escaneo del código de barras de un producto alimenticio
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

        //Metodo que llama a la API de Openfood con un código de barras del producto
        fun fillProduct(barcode: String)

        //Metodo que se ejecuta tras la llamada a la API de Openfood
        fun onFillProductData(result: OpenFoodEntity)
        fun onLoadPantryToUpdate(result: PantryProductEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que obtiene el id de usuario de la App
        fun getUserId(): String

        //Metodo que obtiene las unidade de cantidad de la App
        fun getQuantitiesUnit(): List<QuantityUnit>

        //Metodo que obtiene los atributos del producto de despensa
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>

        //Metodo que realiza la llamada a la API de Openfood con un código de barras y
        //retorna los atributos del producto alimenticio
        fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity>

        //Metodo que inserta un producto de despensa para un usuario en la base de datos
        fun insertPantry(
            barcode: String, name: String, quantity: String,
            quantityUnit: String, place: String, weight: String, price: String,
            expirationDate: String, preferenceDate: String, image: String,
            brand: String, userId: String
        ): MutableLiveData<OneValueEntity>

        //Metodo que actualiza un producto de despensa en la base de datos
        fun updatePantry(
            barcode: String, name: String, quantity: String,
            quantityUnit: String, place: String, weight: String, price: String,
            expirationDate: String, preferenceDate: String, image: String,
            brand: String, idPantry: String
        ): MutableLiveData<SimpleResponseEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de la base de datos
        fun getInstance(context: Context)

        //Metodo que obtiene los atributos de producto de despensa
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>

        //Metodo que obtiene las unidades de cantidad de la App
        fun getQuantitiesUnit(): List<QuantityUnit>

        //Metodo que obtiene los lugares de almacenaje de la App
        fun getStorePlaces(): List<StorePlace>

        //Metodo que obtiene el id de usuario de la App
        fun getUserId(): String

        //Metodo que realiza la llamada a la API de Openfood con un código de barras
        //y devuelve los atributos del producto alimenticio
        fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity>

        //Metodo que inserta un producto de despensa en la base de datos para un usuario
        fun insertPantry(
            barcode: String,
            name: String,
            quantity: String,
            quantityUnit: String,
            place: String,
            weight: String,
            price: String,
            expirationDate: String,
            preferenceDate: String,
            image: String,
            brand: String,
            userId: String
        ): MutableLiveData<OneValueEntity>

        //Metodo que actualiza un producto de despensa en la base de datos dado su id
        fun updatePantry(
            barcode: String,
            name: String,
            quantity: String,
            quantityUnit: String,
            place: String,
            weight: String,
            price: String,
            expirationDate: String,
            preferenceDate: String,
            image: String,
            brand: String,
            idPantry: String
        ): MutableLiveData<SimpleResponseEntity>
    }
}