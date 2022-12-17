package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.ShopProductEntity

interface AddShopContract {
    interface View : Translatable.View {
        fun onLoadShopToUpdate(shopProductEntity: ShopProductEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun getUserId(): String
        fun getQuantitiesUnit(): List<QuantityUnit>
        fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity>
    }

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
        )

        fun updateShop(
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        )

    }
}