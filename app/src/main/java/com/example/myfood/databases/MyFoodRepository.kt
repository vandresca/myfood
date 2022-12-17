package com.example.myfood.databases

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.RoomSingleton
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.StorePlace
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.core.RetrofitHelper
import com.example.myfood.mvvm.data.model.*
import com.example.myfood.mvvm.data.network.MySQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MyFoodRepository {
    private lateinit var dbSQLite: RoomSingleton
    private lateinit var dbMySQL: Retrofit

    fun getInstance(application: Context) {
        dbSQLite = RoomSingleton.getInstance(application)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    fun getTranslations(language: Int, screen: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, screen)
    }

    fun getQuantitiesUnit(): List<QuantityUnit> {
        return dbSQLite.sqliteDao().getQuantitiesUnit()
    }

    fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    fun updateUserId(userId: String) {
        dbSQLite.sqliteDao().updateUserId(userId)
    }

    fun getLanguages(): List<String> {
        return dbSQLite.sqliteDao().getLanguages()
    }

    fun getCurrencies(language: Int): List<String> {
        return dbSQLite.sqliteDao().getCurrencies(language)
    }

    fun getCurrentCurrency(): String {
        return dbSQLite.sqliteDao().getCurrentCurrency()
    }

    fun updateCurrentLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    fun updateCurrentCurrency(currency: String) {
        dbSQLite.sqliteDao().updateCurrentCurrency(currency)
    }

    fun deleteQuantityUnit(idQuantityUnit: String) {
        dbSQLite.sqliteDao().deleteQuantityUnit(idQuantityUnit)
    }

    fun addQuantityUnit(quantityUnit: String) {
        dbSQLite.sqliteDao().addQuantityUnit(quantityUnit)
    }

    fun updateQuantityUnit(quantityUnit: String, id: String) {
        dbSQLite.sqliteDao().updateQuantityUnit(quantityUnit, id)
    }

    fun getStorePlaces(): List<StorePlace> {
        return dbSQLite.sqliteDao().getStorePlaces()
    }

    fun deleteStorePlace(idPlace: String) {
        dbSQLite.sqliteDao().deleteStorePlace(idPlace)
    }

    fun addStorePlace(storePlace: String) {
        dbSQLite.sqliteDao().addStorePlace(storePlace)
    }

    fun updateStorePlace(storePlace: String, id: String) {
        dbSQLite.sqliteDao().updateStorePlace(storePlace, id)
    }


    fun insertPantry(
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
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).updatePantry(
                barcode, name, quantity, quantityUnit, place,
                weight, price, expirationDate, preferenceDate, image, brand, idPantry
            )
        }
    }

    fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
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

    fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
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

    fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {
        val mutable: MutableLiveData<ShopProductEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getShopProduct(idShop)
                response.body() ?: ShopProductEntity(
                    "KO",
                    "", "", ""
                )
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java)
                .insertShop(name, quantity, quantityUnit, userId)
        }
    }

    fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).updateShop(name, quantity, quantityUnit, idShop)
        }
    }

    fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changeEmail(email, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getEmail(user: String): MutableLiveData<OneValueEntity> {
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getEmail(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changePassword(password, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getPassword(user: String): MutableLiveData<OneValueEntity> {
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPassword(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getExpirationList(
        expiration: String,
        idUser: String
    ): MutableLiveData<ExpirationListEntity> {
        val mutable: MutableLiveData<ExpirationListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getExpirationList(expiration, idUser)
                response.body() ?: ExpirationListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).removeExpired(idUser)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).sendLink(language, email)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun login(name: String, password: String): MutableLiveData<LoginEntity> {
        val mutable: MutableLiveData<LoginEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).login(name, password)
                response.body() ?: LoginEntity("KO", "")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun deletePantry(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).deletePantry(id)
        }
    }

    fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {
        val mutable: MutableLiveData<PantryListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPantryList(idUser)
                response.body() ?: PantryListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity> {
        val mutable: MutableLiveData<RecipeEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipe(idRecipe, language)
                response.body() ?: RecipeEntity(
                    "KO",
                    "",
                    "",
                    "",
                    ""
                )
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getRecipeList(language: String): MutableLiveData<RecipeListEntity> {
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipeList(language)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getRecipesSuggested(language: String): MutableLiveData<RecipeListEntity> {
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipesSuggested(language)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {
        val mutable: MutableLiveData<ShopListEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getShopList(idUser)
                response.body() ?: ShopListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun deleteShop(idShop: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbMySQL.create(MySQLApi::class.java).deleteShop(idShop)
        }
    }

    fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String,
    ): MutableLiveData<SimpleResponseEntity> {
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).insertUser(name, surnames, email, password)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getNutrients(): MutableLiveData<NutrientGroupEntity> {
        val mutable: MutableLiveData<NutrientGroupEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getNutrients()
                response.body() ?: NutrientGroupEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }

    fun getNutrientsByType(typeNutrient: String, idFood: String):
            MutableLiveData<NutrientListTypeEntity> {
        val mutable: MutableLiveData<NutrientListTypeEntity> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getNutrientsByType(typeNutrient, idFood)
                response.body() ?: NutrientListTypeEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }
        return mutable
    }
}