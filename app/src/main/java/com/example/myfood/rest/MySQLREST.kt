package com.example.myfood.rest

import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.addshopproduct.AddShopFragment
import com.example.myfood.mvp.config.ConfigFragment
import com.example.myfood.mvp.expiration.ExpirationListPresenter
import com.example.myfood.mvp.recipe.RecipePresenter
import com.example.myfood.mvp.recipelist.RecipeListPresenter

class MySQLREST {
    companion object {
        private const val LOGIN_URL = "http://vandresc-uoc.epizy.com/login/getUserId.php"
        private const val INSERT_USER_URL = "http://vandresc-uoc.epizy.com/signup/insertUser.php"
        private const val GET_FOOD_PRODUCT_URL = "http://vandresc-uoc.epizy.com/getFoodProduct.php"
        private const val PANTRY_GET_LIST_URL =
            "http://vandresc-uoc.epizy.com/pantry/getPantryList.php"
        private const val PANTRY_DELETE_ITEM_URL =
            "http://vandresc-uoc.epizy.com/pantry/deletePantryProduct.php"
        private const val PANTRY_INSERT_ITEM_URL =
            "http://vandresc-uoc.epizy.com/pantry/insertPantry.php"
        private const val PANTRY_UPDATE_ITEM_URL =
            "http://vandresc-uoc.epizy.com/pantry/updatePantry.php"
        private const val GET_PANTRY_PRODUCT_ITEM_URL =
            "http://vandresc-uoc.epizy.com/pantry/getPantryProduct.php"
        private const val SHOP_INSERT_ITEM_URL = "http://vandresc-uoc.epizy.com/shop/insertShop.php"
        private const val SHOP_UPDATE_ITEM_URL = "http://vandresc-uoc.epizy.com/shop/updateShop.php"
        private const val SHOP_GET_LIST_URL = "http://vandresc-uoc.epizy.com/shop/getShopList.php"
        private const val SHOP_DELETE_ITEM_URL = "http://vandresc-uoc.epizy.com/shop/deleteShop.php"
        private const val GET_SHOP_PRODUCT_ITEM_URL =
            "http://vandresc-uoc.epizy.com/shop/getShopProduct.php"
        private const val EXPIRATION_GET_LIST_URL =
            "http://vandresc-uoc.epizy.com/expiration/getExpirationList.php"
        private const val REMOVE_ALL_EXPIRED_URL =
            "http://vandresc-uoc.epizy.com/expiration/removeExpired.php"
        private const val GET_RECIPES_ITEM_URL =
            "http://vandresc-uoc.epizy.com/recipe/getRecipeList.php"
        private const val GET_RECIPE_INFO_URL = "http://vandresc-uoc.epizy.com/recipe/getRecipe.php"
        private const val GET_RECIPES_SUGGESTED_URL =
            "http://vandresc-uoc.epizy.com/recipe/getRecipesSuggested.php"
        private const val CONFIG_CHANGE_MAIL_URL =
            "http://vandresc-uoc.epizy.com/config/changeEmail.php"
        private const val CONFIG_GET_MAIL_URL = "http://vandresc-uoc.epizy.com/config/getEmail.php"

        fun login(name: String, password: String, callback: (String?) -> Unit) {
            RESTRequest.request("$LOGIN_URL?n=$name&p=$password", callback)
        }

        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String,
            callback: (String?) -> Unit
        ) {
            RESTRequest.request(
                "$INSERT_USER_URL?n=$name&s=$surnames&e=$email&p=$password",
                callback
            )
        }

        /**
        fun getFoodProduct(application: AddPantryFragment, nameProduct:String){
        RESTRequest.request("$GET_FOOD_PRODUCT_URL?p=$nameProduct"){ response->application.onCheckProduct(response)}
        }
         **/

        fun getPantryList(
            idUser: String,
            callback: (String?) -> Unit
        ) {
            RESTRequest.request("$PANTRY_GET_LIST_URL?u=$idUser") { response -> callback(response) }
        }

        fun deletePantry(id: String) {
            RESTRequest.request("$PANTRY_DELETE_ITEM_URL?id=$id") {}
        }

        fun insertPantry(
            application: AddPantryFragment,
            barcode: String, name: String, quantity: String, quantityUnit: String,
            place: String, weight: String, price: String, expirationDate: String,
            preferenceDate: String, image: String, brand: String, idUser: String
        ) {
            var url = "$PANTRY_INSERT_ITEM_URL?cb=$barcode&n=$name&q=$quantity&qu=$quantityUnit"
            url = "$url&p=$place&w=$weight&pr=$price"
            url = "$url&ed=$expirationDate&pd=$preferenceDate"
            url = "$url&i=$image&b=$brand&u=$idUser"

            RESTRequest.request(url) { response -> application.onInsertedProduct(response) }
        }

        fun updatePantry(
            application: AddPantryFragment,
            barcode: String, name: String, quantity: String, quantityUnit: String,
            place: String, weight: String, price: String, expirationDate: String,
            preferenceDate: String, image: String, brand: String, idPantry: String
        ) {
            var url = "$PANTRY_UPDATE_ITEM_URL?cb=$barcode&n=$name&q=$quantity&qu=$quantityUnit"
            url = "$url&p=$place&w=$weight&pr=$price"
            url = "$url&ed=$expirationDate&pd=$preferenceDate"
            url = "$url&i=$image&b=$brand&id=$idPantry"

            RESTRequest.request(url) { response -> application.onUpdatedProduct(response) }
        }

        fun getPantryProduct(idPantry: String, callback: (String?) -> Unit) {
            RESTRequest.request("$GET_PANTRY_PRODUCT_ITEM_URL?id=$idPantry")
            { response -> callback(response) }
        }

        fun insertShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        ) {
            RESTRequest.request("$SHOP_INSERT_ITEM_URL?n=$name&q=$quantity&qu=$quantityUnit&u=$userId") { response ->
                application.onInsertedShop(
                    response
                )
            }
        }

        fun updateShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        ) {
            RESTRequest.request("$SHOP_UPDATE_ITEM_URL?n=$name&q=$quantity&qu=$quantityUnit&id=$idShop") { response ->
                application.onUpdatedShop(
                    response
                )
            }
        }

        fun getShopList(userId: String, callback: (String?) -> Unit) {
            RESTRequest.request("$SHOP_GET_LIST_URL?u=$userId") { response -> callback(response) }
        }

        fun deleteShop(idShop: String) {
            RESTRequest.request("$SHOP_DELETE_ITEM_URL?id=$idShop") {}
        }

        fun getShopProduct(idShop: String, callback: (String?) -> Unit) {
            RESTRequest.request("$GET_SHOP_PRODUCT_ITEM_URL?id=$idShop")
            { response -> callback(response) }
        }

        fun getExpirationList(
            application: ExpirationListPresenter,
            expiration: String,
            idUser: String
        ) {
            RESTRequest.request("$EXPIRATION_GET_LIST_URL?e=$expiration&u=$idUser") { response ->
                application.loadData(
                    response
                )
            }
        }

        fun removeExpired(application: ExpirationListPresenter, idUser: String) {
            RESTRequest.request("$REMOVE_ALL_EXPIRED_URL?u=$idUser") { response ->
                application.onRemovedExpired(
                    response
                )
            }
        }

        fun getRecipeList(application: RecipeListPresenter, language: String) {
            RESTRequest.request("$GET_RECIPES_ITEM_URL?l=$language") { response ->
                application.loadData(
                    response
                )
            }
        }

        fun getRecipesSuggested(application: RecipeListPresenter, language: String) {
            RESTRequest.request("$GET_RECIPES_SUGGESTED_URL?l=$language") { response ->
                application.loadSuggested(
                    response
                )
            }
        }

        fun getRecipe(application: RecipePresenter, idRecipe: String, language: String) {
            RESTRequest.request("$GET_RECIPE_INFO_URL?r=$idRecipe&l=$language") { response ->
                application.loadData(
                    response
                )
            }
        }

        fun changeEmail(application: ConfigFragment, email: String, user: String) {
            RESTRequest.request("$CONFIG_CHANGE_MAIL_URL?e=$email&u=$user") { response ->
                application.onChangeEmail(
                    response
                )
            }
        }

        fun getEmail(application: ConfigFragment, user: String) {
            RESTRequest.request("$CONFIG_GET_MAIL_URL?u=$user") { response ->
                application.onGottenEmail(
                    response
                )
            }
        }
    }
}