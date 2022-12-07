package com.example.myfood.interfaces

import androidx.lifecycle.LifecycleOwner
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface Translatable {
    interface View {
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
    }

    interface Model {
        fun getTranslations(
            application: LifecycleOwner, language: Int = LanguageType.ENGLISH.int,
            callback: (List<Translation>) -> Unit
        )

        fun getCurrentLanguage(application: LifecycleOwner, callback: (String) -> Unit)
    }
}