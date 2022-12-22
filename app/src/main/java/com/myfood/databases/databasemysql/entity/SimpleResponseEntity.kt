package com.myfood.databases.databasemysql.entity

import com.google.gson.annotations.SerializedName

// Entidad que solo serializa la respuesta de una petición API
class SimpleResponseEntity(
    @SerializedName("response") val status: String
)