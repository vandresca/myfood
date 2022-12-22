package com.myfood.databases

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.MySQLApi
import com.myfood.databases.databasemysql.RetrofitHelper
import com.myfood.databases.databasemysql.entity.*
import com.myfood.databases.databasesqlite.RoomSingleton
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MyFoodRepository {

    // Declaraciones de las referencias a la bases de datos
    private lateinit var dbSQLite: RoomSingleton
    private lateinit var dbMySQL: Retrofit

    // Método que crea las instancias para las base de datos de SQLite
    // y MySQL
    fun getInstance(context: Context) {
        dbSQLite = RoomSingleton.getInstance(context)
        dbMySQL = RetrofitHelper.getRetrofit()
    }

    // Método que devuelve el lenguaje de la App
    fun getCurrentLanguage(): String {
        return dbSQLite.sqliteDao().getCurrentLanguage()
    }

    // Método que devuelve las traducciones para un idioma y pantalla concreta
    fun getTranslations(language: Int, screen: Int): List<Translation> {
        return dbSQLite.sqliteDao().getTranslations(language, screen)
    }

    // Método que devuelve las unidades de cantidad de la App
    fun getQuantitiesUnit(): List<QuantityUnit> {
        return dbSQLite.sqliteDao().getQuantitiesUnit()
    }

    // Método que devuelve el id de usuario de la App
    fun getUserId(): String {
        return dbSQLite.sqliteDao().getUserId()
    }

    // Método que actualiza el id de usuario de la App
    fun updateUserId(userId: String) {
        dbSQLite.sqliteDao().updateUserId(userId)
    }

    // Método que devuelve el grupo de idiomas de la App
    fun getLanguages(): List<String> {
        return dbSQLite.sqliteDao().getLanguages()
    }

    // Método que devuelve el grupo de tipo de monedas de la App
    fun getCurrencies(language: Int): List<String> {
        return dbSQLite.sqliteDao().getCurrencies(language)
    }

    // Método que devuelve el tipo de moneda de la App
    fun getCurrentCurrency(): String {
        return dbSQLite.sqliteDao().getCurrentCurrency()
    }

    // Método que actualiza el lenguaje de la App
    fun updateCurrentLanguage(language: String) {
        dbSQLite.sqliteDao().updateCurrentLanguage(language)
    }

    // Método que actualiza el tipo de moneda de la App
    fun updateCurrentCurrency(currency: String) {
        dbSQLite.sqliteDao().updateCurrentCurrency(currency)
    }

    // Método que elimina una unidad de cantidad dada su id
    fun deleteQuantityUnit(idQuantityUnit: String) {
        dbSQLite.sqliteDao().deleteQuantityUnit(idQuantityUnit)
    }

    // Método que añade una nueva unidad de cantidad a la App
    fun addQuantityUnit(quantityUnit: String) {
        dbSQLite.sqliteDao().addQuantityUnit(quantityUnit)
    }

    // Método que actualiza una unidad de cantidad dada su id
    fun updateQuantityUnit(quantityUnit: String, id: String) {
        dbSQLite.sqliteDao().updateQuantityUnit(quantityUnit, id)
    }

    // Método que devuelve los lugares de almacenaje de la App
    fun getStorePlaces(): List<StorePlace> {
        return dbSQLite.sqliteDao().getStorePlaces()
    }

    // Método que elimina un lugar de almacenaje a partir su id
    fun deleteStorePlace(idPlace: String) {
        dbSQLite.sqliteDao().deleteStorePlace(idPlace)
    }

    // Método que añade un lugar de almacenaje a la App
    fun addStorePlace(storePlace: String) {
        dbSQLite.sqliteDao().addStorePlace(storePlace)
    }

    // Método que actualiza un lugar de almacenaje a partir de su id
    fun updateStorePlace(storePlace: String, id: String) {
        dbSQLite.sqliteDao().updateStorePlace(storePlace, id)
    }

    // Método que inserta un producto de despensa en la base de datos MySQL
    fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String, brand: String, userId: String
    ): MutableLiveData<OneValueEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()

        // Ejecutamos el metodo insertPantry en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).insertPantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, userId
                )
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que actualiza un producto de despensa en la base de datos MySQL a partir
    // de su id
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
    ): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo updatePantry en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).updatePantry(
                    barcode,
                    name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, idPantry
                )
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que obtiene los atributos de un producto de despensa de la base de datos MySQL
    // a partir de su id
    fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<PantryProductEntity> = MutableLiveData()

        // Ejecutamos el metodo getPantryProduct en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
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

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que obtiene los atributos de un producto alimenticio a partir de una url
    // con su código de barras de la API de Openfood
    fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OpenFoodEntity> = MutableLiveData()

        // Ejecutamos el metodo getOpenFoodProduct en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getOpenFoodProduct(url)
                val emptyObject = OpenFoodProductEntity(
                    "",
                    "", "", "", ""
                )
                response.body() ?: OpenFoodEntity(0, emptyObject)
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve los atributos de un producto de compra a partir de su id
    // de la base de datos MysQL
    fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<ShopProductEntity> = MutableLiveData()

        // Ejecutamos el metodo getShopProduct en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
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

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que inserta un producto de compra en la base de datos MySQL
    fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ): MutableLiveData<OneValueEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()

        // Ejecutamos el metodo insertShop en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).insertShop(
                    name,
                    quantity, quantityUnit, userId
                )
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que actualiza un producto de compra en la base de datos MySQL a partir de su
    // id
    fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo updateShop en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).updateShop(
                    name,
                    quantity, quantityUnit, idShop
                )
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que cambia el email de un usuario concreto a partir de un id de usuario
    fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo changeEmail en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changeEmail(email, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que obtiene el email de un usuario a partir de un id de usuario
    fun getEmail(user: String): MutableLiveData<OneValueEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()

        // Ejecutamos el metodo getEmail en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getEmail(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que cambia la contraseña de un usuario a partir de un id de usuario
    fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo changePassword en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).changePassword(password, user)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve la contraseña de usuario a partir de un id de usuario
    fun getPassword(user: String): MutableLiveData<OneValueEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()

        // Ejecutamos el metodo getPassword en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPassword(user)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve una lista de productos de despensa a partir su caducidad
    // (todos, caducados, de 0 a 10 días, más de 10 días) y el id de usurio
    fun getExpirationList(
        expiration: String,
        idUser: String
    ): MutableLiveData<ExpirationListEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<ExpirationListEntity> = MutableLiveData()

        // Ejecutamos el metodo getExpirationList en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getExpirationList(expiration, idUser)
                response.body() ?: ExpirationListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que elimina todos los productos de despensa caducados dado un id de usuario
    fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo removeExpired en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).removeExpired(idUser)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que envia un link para resetear la contraseña a partir de un correo (si
    // existe) y un idioma
    fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo sendLink en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).sendLink(language, email)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que dado un nombre y una contraseña devuelve si coinciden en la base de datos
    // MySQL o no y el id de usuario.
    fun login(name: String, password: String): MutableLiveData<LoginEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<LoginEntity> = MutableLiveData()

        // Ejecutamos el metodo login en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).login(name, password)
                response.body() ?: LoginEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que elimina un producto de despensa a partir de su id
    fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo deletePantry en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).deletePantry(id)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve la lista de productos de despensa para un id de usuario
    fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<PantryListEntity> = MutableLiveData()

        // Ejecutamos el metodo getPantryList en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getPantryList(idUser)
                response.body() ?: PantryListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve los atributos de una receta dada su id y un idioma
    fun getRecipe(idRecipe: String, language: String): MutableLiveData<RecipeEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<RecipeEntity> = MutableLiveData()

        // Ejecutamos el metodo getRecipe en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
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

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve una lista de recetas de la base de datos MySQL dado
    // un idioma
    fun getRecipeList(language: String): MutableLiveData<RecipeListEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()

        // Ejecutamos el metodo getRecipeList en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getRecipeList(language)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve la lista de recetas sugeridas según los productos de despensa
    // de la base de datos a partir del idioma el id de usuario
    fun getRecipesSuggested(language: String, user: String): MutableLiveData<RecipeListEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<RecipeListEntity> = MutableLiveData()

        // Ejecutamos el metodo getRecipesSuggested en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getRecipesSuggested(language, user)
                response.body() ?: RecipeListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que obtiene la lista de productos de compra para un id de usuario concreto
    fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<ShopListEntity> = MutableLiveData()

        // Ejecutamos el metodo getShopList en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getShopList(idUser)
                response.body() ?: ShopListEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que elimina un producto de compra a partir de su id
    fun deleteShop(idShop: String): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo deleteShop en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).deleteShop(idShop)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que inserta un usuario en la base de datos MySQL
    fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String,
    ): MutableLiveData<OneValueEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<OneValueEntity> = MutableLiveData()

        // Ejecutamos el metodo insertUser en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).insertUser(name, surnames, email, password)
                response.body() ?: OneValueEntity("KO", "")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que elimina un usuario en la base de datos MySQL a partir del id de usuario
    fun deleteUser(
        id: String,
    ): MutableLiveData<SimpleResponseEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<SimpleResponseEntity> = MutableLiveData()

        // Ejecutamos el metodo deleteUser en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).deleteUser(id)
                response.body() ?: SimpleResponseEntity("KO")
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve el tipo de grupo de nutrientes en un idioma concreto a partir
    // de su idioma
    fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<NutrientGroupEntity> = MutableLiveData()

        // Ejecutamos el metodo getNutrients en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response = dbMySQL.create(MySQLApi::class.java).getNutrients(language)
                response.body() ?: NutrientGroupEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }

    // Método que devuelve los nutrientes de un producto alimenticio a partir del tipo de grupo
    // de nutriente, id del alimento e idioma
    fun getNutrientsByType(typeNutrient: String, idFood: String, language: String):
            MutableLiveData<NutrientListTypeEntity> {

        // Declaramos un MutableLiveData de tipo OneValueEntity
        val mutable: MutableLiveData<NutrientListTypeEntity> = MutableLiveData()

        // Ejecutamos el metodo getNutrientsByType en otro hilo e insertamos el valor dentro del
        // MutableLiveData para recuperarlo de vuelta al hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            val value = withContext(Dispatchers.IO) {
                val response =
                    dbMySQL.create(MySQLApi::class.java).getNutrientsByType(
                        typeNutrient,
                        idFood, language
                    )
                response.body() ?: NutrientListTypeEntity("KO", emptyList())
            }
            mutable.postValue(value)
        }

        // Devolvemos el objeto MutableLiveData
        return mutable
    }
}