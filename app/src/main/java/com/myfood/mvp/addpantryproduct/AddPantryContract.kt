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
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun fillProduct(barcode: String)
        fun onFillProductData(result: OpenFoodEntity)
        fun onLoadPantryToUpdate(result: PantryProductEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun getUserId(): String
        fun getQuantitiesUnit(): List<QuantityUnit>
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity>
        fun insertPantry(
            barcode: String, name: String, quantity: String,
            quantityUnit: String, place: String, weight: String, price: String,
            expirationDate: String, preferenceDate: String, image: String,
            brand: String, userId: String
        ): MutableLiveData<OneValueEntity>

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
        fun getInstance(context: Context)
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun getQuantitiesUnit(): List<QuantityUnit>
        fun getPlaces(): List<StorePlace>
        fun getUserId(): String
        fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity>
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