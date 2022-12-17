package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.PantryProductEntity

interface PantryFeatureContract {
    interface View : Translatable.View {
        fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun deletePantry(idPurchase: String)
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>
        fun deletePantry(id: String)
    }
}