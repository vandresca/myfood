package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.interfaces.Translatable

interface AddPantryContract {
    interface View : Translatable.View {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun fillProduct(barcode: String)
        fun onFillProductData(response: String?)
        fun onInsertedProduct(response: String?)
        fun onUpdatedProduct(response: String?)
        fun onQuantitiesLoaded(quantitiesUnit: List<QuantityUnit>)
        fun onPlacesLoaded(places: List<StorePlace>)
        fun onLoadPantryToUpdate(response: String?)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun getPantryProduct(idPantry: String, callback: (String?) -> Unit)
        fun getQuantitiesUnit(application: LifecycleOwner, callback: (List<QuantityUnit>) -> Unit)
        fun getPlaces(application: LifecycleOwner, callback: (List<StorePlace>) -> Unit)
        fun getUserId(application: LifecycleOwner, callback: (String) -> Unit)
        fun insertPantry(
            application: AddPantryFragment,
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
        )

        fun updatePantry(
            application: AddPantryFragment,
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
        )


    }
}