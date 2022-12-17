package com.example.myfood.enum

enum class ExpirationType(val type: String) {
    EXPIRATION_ALL("All"),
    EXPIRATION_OTO10DAYS("0to10days"),
    EXPIRATION_MORE10DAYS("more10days"),
    EXPIRATION_EXPIRED("expired")
}