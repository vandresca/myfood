package com.example.myfood.mvp.optionaddpantry

import android.content.Context
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface OptionAddPantryContract {
    interface View {
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
    }

    interface Presenter

    interface Model {
        fun getInstance(application: Context)
        fun getTranslations(
            application: OptionAddPantryFragment,
            language: Int = LanguageType.ENGLISH.int
        )

        fun getCurrentLanguage(application: OptionAddPantryFragment)
    }
}