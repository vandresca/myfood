package com.example.myfood.interfaces

import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

// Interfaz que obliga a implementar los métodos necesarios
// para la traducción de textos de la App
interface Translatable {

    //Vista
    interface View {
        // Establece los textos traducidos a los elementos lista
        fun setTranslations()
    }

    //Presentador
    interface Presenter {

        // Obtiene el lenguage de la App
        fun getCurrentLanguage(): String

        // Obtiene las traducciones para un tipo de idioma concreto
        fun getTranslations(language: Int): MutableMap<String, Translation>
    }

    //Modelo
    interface Model {

        // Obtiene el lenguaje de la App
        fun getCurrentLanguage(): String

        // Obtiene las traducciones para un tipo de idioma concreto, si no se especifica
        // se escoje el inglés.
        fun getTranslations(language: Int = LanguageType.ENGLISH.int): List<Translation>
    }
}