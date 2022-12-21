package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa el estado despues de hacer login y el id de usuario
// si es el caso.
data class LoginEntity(
    @SerializedName("response") val status: String,
    @SerializedName("idUser") val idUser: String
)