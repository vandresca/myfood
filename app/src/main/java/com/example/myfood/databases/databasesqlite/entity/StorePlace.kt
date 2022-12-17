package com.example.myfood.databases.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class StorePlace(
    @PrimaryKey
    @NotNull
    val idStorePlace: Int,
    @NotNull
    val storePlace: String
)
