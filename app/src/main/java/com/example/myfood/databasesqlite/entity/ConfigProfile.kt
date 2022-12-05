package com.example.myfood.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class ConfigProfile(
    @PrimaryKey
    @NotNull
    val parameter: String,
    @NotNull
    val value: String
)