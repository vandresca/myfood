package com.example.myfood.interfaces

import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface Translatable {
    interface View
    interface Presenter {
        fun getCurrentLanguage(): String
        fun getTranslations(language: Int = LanguageType.ENGLISH.int): MutableMap<String, Translation>
    }

    interface Model {
        fun getCurrentLanguage(): String
        fun getTranslations(language: Int): List<Translation>
    }
}