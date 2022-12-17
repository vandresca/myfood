package com.example.myfood.databases.databasesqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class ConfigProfile(
    @PrimaryKey
    @NotNull
    val idConfigProfile: Int,
    @NotNull
    val parameter: String,
    @NotNull
    val value: String
)