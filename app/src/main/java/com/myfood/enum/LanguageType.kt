package com.myfood.enum

// Enumeración para los tipos de idioma
// SPANISH: Español
// ENGLISH: Inglés
// FRENCH: Francés
// GERMAN: Alemán
// CATALAN: Catalan
enum class LanguageType(val int: Int) {
    SPANISH(1),
    ENGLISH(2),
    FRENCH(3),
    GERMAN(4),
    CATALAN(5)
}