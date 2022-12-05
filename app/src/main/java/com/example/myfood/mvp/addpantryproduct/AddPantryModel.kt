package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.rest.MySQLREST

class AddPantryModel : AddPantryContract.Model {
    lateinit var dbSQLite: RoomSingleton

    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getQuantitiesUnit(application: AddPantryFragment) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getQuantitiesUnit()
        values.observe(
            application,
            Observer<List<String>> { application.onQuantitiesLoaded(it) })
    }

    override fun getPlaces(application: AddPantryFragment) {
        val values: LiveData<List<String>> = dbSQLite.sqliteDao().getStorePlaces()
        values.observe(
            application,
            Observer<List<String>> { application.onPlacesLoaded(it) })
    }

    override fun getUserId(application: AddPantryFragment) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(
            application,
            Observer<String> { application.onUserIdLoaded(it) })
    }

    override fun insertPantry(
        application: AddPantryFragment, barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String, brand: String, userId: String
    ) {
        MySQLREST.insertPantry(
            application, barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, userId
        )
    }

    override fun updatePantry(
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
    ) {
        MySQLREST.updatePantry(
            application, barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, idPantry
        )
    }

    override fun getPantryProduct(application: AddPantryFragment, idPantry: String) {
        MySQLREST.getPantryProduct(application, idPantry)
    }
}