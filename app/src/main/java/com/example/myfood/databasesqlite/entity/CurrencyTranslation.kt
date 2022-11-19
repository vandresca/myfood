package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class CurrencyTranslation(
    @PrimaryKey
    @NotNull
    val idCurrTranslation: Int,
    @NotNull
    val idCurrency: Int,
    @NotNull
    val idLanguage: Int,
    @NotNull
    val symbol: String
)