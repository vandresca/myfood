package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

// Entidad que serializa el estado de respuesta y un único valor de la
// petición al scrip PHP.
class OneValueEntity(
    @SerializedName("response") val status: String,
    @SerializedName("value") val value: String
)