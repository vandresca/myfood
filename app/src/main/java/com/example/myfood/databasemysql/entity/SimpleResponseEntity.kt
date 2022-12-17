package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class SimpleResponseEntity(
    @SerializedName("response") val status: String
)