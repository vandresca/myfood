package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Screen(
    @PrimaryKey
    @NotNull
    val idScreen: Int,
    @NotNull
    val screen: String
)