package com.example.myfood.databasesqlite

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Word(
    @PrimaryKey
    @NotNull
    val idWord: Int,
    @NotNull
    val word: String
)
