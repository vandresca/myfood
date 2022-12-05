package com.example.myfood.rest

import com.example.myfood.mvp.addpantryproduct.AddPantryFragment

class OpenFoodREST {
    companion object {
        private const val OPEN_FOOD_API = "https://es.openfoodfacts.org/api/v0/product/"

        fun openFoodRequest(application: AddPantryFragment, barcode: String) {
            RESTRequest.request("$OPEN_FOOD_API$barcode.json") { response ->
                application.onFillProductData(
                    response
                )
            }
        }
    }
}