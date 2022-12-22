package com.myfood.enum

// Enumeración para los tipos de caducidad
// All: Todos
// 0TO10DAYS: De 0 a 10 días para caducar
// MORE10DAYS: Más de 10 días para caducar
// EXPIRED: Caducado
enum class ExpirationType(val type: String) {
    EXPIRATION_ALL("All"),
    EXPIRATION_OTO10DAYS("0to10days"),
    EXPIRATION_MORE10DAYS("more10days"),
    EXPIRATION_EXPIRED("expired")
}