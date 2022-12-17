package com.example.myfood.mvp.optionaddpantry

import android.content.Context
import com.example.myfood.interfaces.Translatable

interface OptionAddPantryContract {
    interface View : Translatable.View {
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter
    interface Model : Translatable.Model {
        fun getInstance(application: Context)
    }
}