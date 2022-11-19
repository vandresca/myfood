package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Language(
    @PrimaryKey
    @NotNull
    val idLanguage: Int,
    @NotNull
    val language: String
)
