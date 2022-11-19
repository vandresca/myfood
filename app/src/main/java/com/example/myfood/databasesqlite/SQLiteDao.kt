package com.example.myfood.databasesqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
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

    @Query("SELECT language FROM Language")
    fun getLanguages(): LiveData<List<String>>

    @Query(
        "SELECT symbol " +
                "FROM CurrencyTranslation " +
                "WHERE idLanguage = :language " +
                " and idCurrency =:currency"
    )
    fun getCurrency(language: Int, currency: Int): LiveData<List<String>>

    @Query("SELECT quantityUnit FROM QuantityUnit")
    fun getQuantitiesUnit(): LiveData<List<String>>

    @Query("SELECT storePlace FROM StorePlace")
    fun getStorePlaces(): LiveData<List<String>>

}