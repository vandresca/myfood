package com.myfood.databases.databasesqlite

import androidx.room.Dao
import androidx.room.Query
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation

@Dao
interface SQLiteDao {

    // Obtiene la lista traducciones para un idioma y pantalla concreta.
    @Query(
        "SELECT w.word,  wt.text " +
                "FROM WordTranslation wt, Word w, ScreenWord sw " +
                "WHERE sw.idWord = w.idWord " +
                "and wt.idWord = w.idWord " +
                "and sw.idScreen=:screen " +
                "and wt.idLanguage=:language"
    )
    fun getTranslations(language: Int, screen: Int): List<Translation>

    // Obtiene el lenguaje la App
    @Query("SELECT value FROM ConfigProfile WHERE parameter='language'")
    fun getCurrentLanguage(): String

    // Actuliza el lenguaje la App
    @Query("UPDATE ConfigProfile SET value =:language WHERE parameter='language'")
    fun updateCurrentLanguage(language: String)

    // Actualiza el tipo de moneda que usa la App
    @Query("UPDATE ConfigProfile SET value =:currency WHERE parameter='symbol'")
    fun updateCurrentCurrency(currency: String)

    // Obtiene la lista de tipo de monedas de la app
    @Query(
        "SELECT symbol " +
                "FROM CurrencyTranslation " +
                "WHERE idLanguage = :language"
    )
    fun getCurrencies(language: Int): List<String>

    // Obtiene el tipo de moneda de la App
    @Query("SELECT value FROM ConfigProfile WHERE parameter='symbol'")
    fun getCurrentCurrency(): String

    // Actualiza el id de usuario de la App
    @Query("UPDATE ConfigProfile SET value =:userId WHERE parameter='userId'")
    fun updateUserId(userId: String)

    // Obtiene el id de usuario de la App
    @Query("SELECT value FROM ConfigProfile WHERE parameter='userId'")
    fun getUserId(): String

    // Obtiene la lista de idiomas disponibles de la App
    @Query("SELECT language FROM Language")
    fun getLanguages(): List<String>

    // Obtiene la lista de unidades de cantidad disponibles
    @Query("SELECT * FROM QuantityUnit")
    fun getQuantitiesUnit(): List<QuantityUnit>

    // Añade una unidad de cantidad
    @Query("INSERT INTO QuantityUnit(quantityUnit) values(:quantityUnit)")
    fun addQuantityUnit(quantityUnit: String)

    // Actualiza una unidad de cantidad por id
    @Query(
        "UPDATE QuantityUnit SET quantityUnit=:quantityUnit" +
                " WHERE idQuantityUnit=:id"
    )
    fun updateQuantityUnit(quantityUnit: String, id: String)

    // Elimina una unidad de cantidad por id
    @Query("DELETE FROM QuantityUnit WHERE idQuantityUnit = :id")
    fun deleteQuantityUnit(id: String)

    // Obtien la lista de lugares de almacenaje disponible
    @Query("SELECT * FROM StorePlace")
    fun getStorePlaces(): List<StorePlace>

    // Añade un lugar de almacenaje
    @Query("INSERT INTO StorePlace(storePlace) values(:place)")
    fun addStorePlace(place: String)

    //Actualiza un lugar de almacenaje por id
    @Query(
        "UPDATE StorePlace SET storePlace=:storePlace " +
                "WHERE idStorePlace = :id"
    )
    fun updateStorePlace(storePlace: String, id: String)

    // Elimina un lugar de almacenaje por id
    @Query("DELETE FROM StorePlace WHERE idStorePlace = :id")
    fun deleteStorePlace(id: String)
}