package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("response") val status: String,
    @SerializedName("idUser") val idUser: String
)