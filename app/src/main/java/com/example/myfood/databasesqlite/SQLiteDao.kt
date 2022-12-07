package com.example.myfood.databasesqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation

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
    fun getTranslations(language: Int, screen: Int): LiveData<List<Translation>>

    @Query("SELECT value FROM ConfigProfile WHERE parameter='language'")
    fun getCurrentLanguage(): LiveData<String>

    @Query("UPDATE ConfigProfile SET value =:language WHERE parameter='language'")
    fun updateCurrentLanguage(language: String)

    @Query("UPDATE ConfigProfile SET value =:currency WHERE parameter='symbol'")
    fun updateCurrentCurrency(currency: String)

    @Query("UPDATE ConfigProfile SET value =:userId WHERE parameter='userId'")
    fun updateUserId(userId: String)

    @Query("SELECT value FROM ConfigProfile WHERE parameter='userId'")
    fun getUserId(): LiveData<String>

    @Query("SELECT language FROM Language")
    fun getLanguages(): LiveData<List<String>>

    @Query("SELECT * FROM QuantityUnit")
    fun getQuantitiesUnit(): LiveData<List<QuantityUnit>>

    @Query("SELECT * FROM StorePlace")
    fun getStorePlaces(): LiveData<List<StorePlace>>

    @Query("INSERT INTO StorePlace(storePlace) values(:place)")
    fun addPlace(place: String)

    @Query(
        "SELECT symbol " +
                "FROM CurrencyTranslation " +
                "WHERE idLanguage = :language"
    )
    fun getCurrencies(language: Int): LiveData<List<String>>

    @Query("SELECT value FROM ConfigProfile WHERE parameter='symbol'")
    fun getCurrentCurrency(): LiveData<String>

}