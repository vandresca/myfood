package com.example.myfood.mvp.addplace

import android.content.Context
import com.example.myfood.interfaces.Translatable

interface AddStorePlaceContract {
    interface View : Translatable.View {
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun addStorePlace(storePlace: String)
        fun updateStorePlace(storePlace: String, idStorePlace: String)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun addStorePLace(storePlace: String)
        fun updateStorePlace(storePlace: String, id: String)
    }
}