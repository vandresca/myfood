package com.example.myfood.mvvm.data.model

import com.google.gson.annotations.SerializedName

class PantryProductEntity(
    @SerializedName("response") val status: String,
    @SerializedName("id_pantry") val idPantry: String,
    @SerializedName("id_food") val idFood: String,
    @SerializedName("image") val image: String,
    @SerializedName("barcode") val barcode: String,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("quantityUnit") val quantityUnit: String,
    @SerializedName("place") val storePlace: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("price") val price: String,
    @SerializedName("expiredDate") val expiredDate: String,
    @SerializedName("preferenceDate") val preferenceDate: String,
    @SerializedName("brand") val brand: String
)