package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class QuantityUnit(

    @PrimaryKey
    @NotNull
    val idQuantityUnit: Int,
    @NotNull
    val quantityUnit: String
)
