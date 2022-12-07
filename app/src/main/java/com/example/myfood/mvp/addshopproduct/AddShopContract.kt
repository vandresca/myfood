package com.example.myfood.mvp.addshopproduct

import androidx.lifecycle.LifecycleOwner
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.interfaces.Translatable

interface AddShopContract {
    interface View : Translatable.View {
        fun onInsertedShop(response: String?)
        fun onUpdatedShop(response: String?)
        fun onLoadShopToUpdate(response: String?)
        fun onQuantitiesLoaded(quantitiesUnit: List<QuantityUnit>)
    }

    interface Model : Translatable.Model {
        fun getShopProduct(idShop: String, callback: (String?) -> Unit)
        fun getQuantitiesUnit(application: LifecycleOwner, callback: (List<QuantityUnit>) -> Unit)
        fun getUserId(application: LifecycleOwner, callback: (String) -> Unit)
        fun insertShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        )

        fun updateShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        )

    }
}