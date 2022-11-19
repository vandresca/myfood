package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class WordTranslation(
    @PrimaryKey
    @NotNull
    val idWordTranslation: Int,
    @NotNull
    val idWord: Int,
    @NotNull
    val idLanguage: Int,
    @NotNull
    val text: String
)
