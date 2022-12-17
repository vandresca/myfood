package com.example.myfood.databases.databasesqlite

import androidx.room.Dao
import androidx.room.Query
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.StorePlace
import com.example.myfood.databases.databasesqlite.entity.Translation

@Dao
interface SQLiteDao {

    @Query(
        "SELECT w.word,  wt.text " +
                "FROM WordTranslation wt, Word w, ScreenWord sw " +
                "WHERE sw.idWord = w.idWord " +
                "and wt.idWord = w.idWord " +
                "and sw.idScreen=:screen " +
                "and wt.idLanguage=:language"
    )
    fun getTranslations(language: Int, screen: Int): List<Translation>

    @Query("SELECT value FROM ConfigProfile WHERE parameter='language'")
    fun getCurrentLanguage(): String

    @Query("UPDATE ConfigProfile SET value =:language WHERE parameter='language'")
    fun updateCurrentLanguage(language: String)

    @Query("UPDATE ConfigProfile SET value =:currency WHERE parameter='symbol'")
    fun updateCurrentCurrency(currency: String)

    @Query("UPDATE ConfigProfile SET value =:userId WHERE parameter='userId'")
    fun updateUserId(userId: String)

    @Query("SELECT value FROM ConfigProfile WHERE parameter='userId'")
    fun getUserId(): String

    @Query("SELECT language FROM Language")
    fun getLanguages(): List<String>


    @Query("SELECT * FROM QuantityUnit")
    fun getQuantitiesUnit(): List<QuantityUnit>

    @Query("SELECT * FROM StorePlace")
    fun getStorePlaces(): List<StorePlace>

    @Query("INSERT INTO StorePlace(storePlace) values(:place)")
    fun addStorePlace(place: String)

    @Query(
        "UPDATE StorePlace SET storePlace=:storePlace " +
                "WHERE idStorePlace = :id"
    )
    fun updateStorePlace(storePlace: String, id: String)

    @Query("DELETE FROM StorePlace WHERE idStorePlace = :id")
    fun deleteStorePlace(id: String)

    @Query("INSERT INTO QuantityUnit(quantityUnit) values(:quantityUnit)")
    fun addQuantityUnit(quantityUnit: String)

    @Query(
        "UPDATE QuantityUnit SET quantityUnit=:quantityUnit" +
                " WHERE idQuantityUnit=:id"
    )
    fun updateQuantityUnit(quantityUnit: String, id: String)

    @Query("DELETE FROM QuantityUnit WHERE idQuantityUnit = :id")
    fun deleteQuantityUnit(id: String)

    @Query(
        "SELECT symbol " +
                "FROM CurrencyTranslation " +
                "WHERE idLanguage = :language"
    )
    fun getCurrencies(language: Int): List<String>

    @Query("SELECT value FROM ConfigProfile WHERE parameter='symbol'")
    fun getCurrentCurrency(): String

}