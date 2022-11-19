package com.example.myfood.databasesqlite

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Currency(
    @PrimaryKey
    @NotNull
    val idCurrency: Int,
    @NotNull
    val currency: String
)