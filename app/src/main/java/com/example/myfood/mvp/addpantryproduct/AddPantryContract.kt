package com.example.myfood.mvp.addpantryproduct

import android.content.Intent

interface AddPantryContract {
    interface View {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun fillProduct(barcode: String)
        fun onFillProductData(response: String?)
        fun onInsertedProduct(response: String?)
        fun onUpdatedProduct(response: String?)
        fun onQuantitiesLoaded(quantitiesUnit: List<String>)
        fun onPlacesLoaded(places: List<String>)
        fun onLoadPantryToUpdate(response: String?)
    }

    interface Model {
        fun getPantryProduct(application: AddPantryFragment, idPantry: String)
        fun getQuantitiesUnit(application: AddPantryFragment)
        fun getPlaces(application: AddPantryFragment)
        fun getUserId(application: AddPantryFragment)
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