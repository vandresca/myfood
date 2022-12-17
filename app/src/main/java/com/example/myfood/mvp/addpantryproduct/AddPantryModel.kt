package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.RoomSingleton
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.OpenFoodEntity
import com.example.myfood.mvvm.data.model.OpenFoodProductEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class AddPantryModel : AddPantryContract.Model {

    lateinit var dbSQLite: RoomSingleton
    lateinit var dbMySQL: Retrofit

    override fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    override fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return dbSQLite.sqliteDao().getQuantitiesUnit()
    }

    override fun getPlaces(): List<StorePlace> {
        return dbSQLite.sqliteDao().getStorePlaces()
    }

    override fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    override fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String, brand: String, userId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).insertPantry(
                barcode, name, quantity, quantityUnit, place,
                weight, price, expirationDate, preferenceDate, image, brand, userId
            )
        }
    }

    override fun updatePantry(
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
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).updatePantry(
                barcode, name, quantity, quantityUnit, place,
                weight, price, expirationDate, preferenceDate, image, brand, idPantry
            )
        }
    }

    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        val mutable: MutableLiveData<PantryProductEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPantryProduct(idPantry)
                response.body() ?: PantryProductEntity(
                    "KO",
                    "", "", "", "",
                    "", "", "", "", "",
                    "", "", "", ""
                )
            }
            mutable.postValue(value)
        }
        return mutable
    }

    override fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
        val mutable: MutableLiveData<OpenFoodEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getOpenFoodProduct(url)
                val emptyObject = OpenFoodProductEntity(
                    "",
                    "", "", "", " "
                )
                response.body() ?: OpenFoodEntity(0, emptyObject)
            }
            mutable.postValue(value)
        }
        return mutable
    }
}