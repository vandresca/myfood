package com.myfood.databases.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class ScreenWord(

    @PrimaryKey
    @NotNull
    val idScreenWord: Int,
    @NotNull
    val idWord: Int,
    @NotNull
    val idScreen: Int
)