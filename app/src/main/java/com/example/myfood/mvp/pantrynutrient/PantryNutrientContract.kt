package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity

interface PantryNutrientContract {
    interface View : Translatable.View {
        fun onNutrientsLoaded(nutrientGEntity: NutrientGroupEntity)
        fun onNutrientsTypeLoaded(nutrientGTEntity: NutrientListTypeEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun deletePantry(idPurchase: String)
        fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity>
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String
        ): MutableLiveData<NutrientListTypeEntity>
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun deletePantry(id: String)
        fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity>
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String
        ): MutableLiveData<NutrientListTypeEntity>
    }
}