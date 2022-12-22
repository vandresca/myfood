package com.myfood.enum

// Enumeración para los tipos de pantalla de la Aplicación
// LOGIN: Pantalla de Login
// SIGN_UP: Pantalla de registro
// YOU_FORGOT_THE_PASSWORD: Pantalla ¿Olvido su contraseña?
// PANTRY_LIST: Pantalla Despensa y Menu de Navegación
// PANTRY_PRODUCT: Pantalla Añadir Despensa, Característica Producto Despensa y
//    y Nutrientes de producto Despensa
// SHOPPING_LIST: Pantalla Compra y Añadir Compra
// EXPIRATION: Pantalla Caducidad
// RECIPES: Pantalla Recetas
// CONFIG: Pantalla Configuración
enum class ScreenType(val int: Int) {
    LOGIN(1),
    SIGN_UP(2),
    YOU_FORGOT_THE_PASSWORD(3),
    PANTRY_LIST(4),
    PANTRY_PRODUCT(5),
    SHOPPING_LIST(6),
    EXPIRATION(7),
    RECIPES(8),
    CONFIG(9)
}