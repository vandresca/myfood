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