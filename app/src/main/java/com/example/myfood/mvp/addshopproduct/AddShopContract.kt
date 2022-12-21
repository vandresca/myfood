package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.ShopProductEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Añadir Producto Compra
interface AddShopContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onLoadShopToUpdate(shopProductEntity: ShopProductEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun getUserId(): String
        fun getQuantitiesUnit(): List<QuantityUnit>
        fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity>
        fun insertShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        ): MutableLiveData<OneValueEntity>

        fun updateShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        ): MutableLiveData<SimpleResponseEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity>
        fun getQuantitiesUnit(): List<QuantityUnit>
        fun getUserId(): String
        fun insertShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        ): MutableLiveData<OneValueEntity>

        fun updateShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        ): MutableLiveData<SimpleResponseEntity>
    }
}