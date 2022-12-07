package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.rest.MySQLREST

class AddPantryModel : AddPantryContract.Model {
    lateinit var dbSQLite: RoomSingleton

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
    }

    override fun getQuantitiesUnit(
        application: LifecycleOwner,
        callback: (List<QuantityUnit>) -> Unit
    ) {
        val values: LiveData<List<QuantityUnit>> = dbSQLite.sqliteDao().getQuantitiesUnit()
        values.observe(application) { callback(it) }
    }

    override fun getPlaces(application: LifecycleOwner, callback: (List<StorePlace>) -> Unit) {
        val values: LiveData<List<StorePlace>> = dbSQLite.sqliteDao().getStorePlaces()
        values.observe(application) { callback(it) }
    }

    override fun getUserId(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getUserId()
        values.observe(application) { callback(it) }
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

    override fun getPantryProduct(idPantry: String, callback: (String?) -> Unit) {
        MySQLREST.getPantryProduct(idPantry, callback)
    }

    override fun getCurrentLanguage(application: LifecycleOwner, callback: (String) -> Unit) {
        val values: LiveData<String> = dbSQLite.sqliteDao().getCurrentLanguage()
        values.observe(application) { callback(it) }
    }

    override fun getTranslations(
        application: LifecycleOwner,
        language: Int,
        callback: (List<Translation>) -> Unit
    ) {
        val values: LiveData<List<Translation>> =
            dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
        values.observe(application) { callback(it) }
    }

}