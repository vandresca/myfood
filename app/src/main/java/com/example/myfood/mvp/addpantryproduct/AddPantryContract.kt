package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.OpenFoodEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity

interface AddPantryContract {
    interface View : Translatable.View {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun fillProduct(barcode: String)
        fun onFillProductData(result: OpenFoodEntity)
        fun onLoadPantryToUpdate(result: PantryProductEntity)
        fun setTranslations()
    }

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
        )

        fun updatePantry(
            barcode: String, name: String, quantity: String,
            quantityUnit: String, place: String, weight: String, price: String,
            expirationDate: String, preferenceDate: String, image: String,
            brand: String, idPantry: String
        )
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
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
        )

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
        )
    }
}