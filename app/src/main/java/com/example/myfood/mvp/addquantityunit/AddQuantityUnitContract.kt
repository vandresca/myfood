package com.example.myfood.mvp.addquantityunit

import android.content.Context
import com.example.myfood.interfaces.Translatable

interface AddQuantityUnitContract {
    interface View : Translatable.View {
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun addQuantityUnit(quantityUnit: String)
        fun updateQuantityUnit(quantityUnit: String, idQuantityUnit: String)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun addQuantityUnit(quantityUnit: String)
        fun updateQuantityUnit(quantityUnit: String, id: String)
    }
}