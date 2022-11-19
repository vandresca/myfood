package com.example.myfood.databasesqlite

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SQLiteDao {

    @Query(
        "SELECT wt.text " +
                "FROM WordTranslation wt, Word w, ScreenWord sw " +
                "WHERE sw.idWord = w.idWord " +
                "and wt.idWord = w.idWord " +
                "and sw.idScreen=:language " +
                "and wt.idLanguage=:screen"
    )
    suspend fun getTranslations(language: Int, screen: Int): List<String>

    @Query("SELECT * FROM Language")
    suspend fun getLanguages(): List<Language>

    @Query(
        "SELECT symbol " +
                "FROM CurrencyTranslation " +
                "WHERE idLanguage = :language " +
                " and idCurrency =:currency"
    )
    suspend fun getCurrency(language: Int, currency: Int): List<String>

}