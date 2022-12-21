package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Nutrientes de Producto Despensa
interface PantryNutrientContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onNutrientsLoaded(nutrientGEntity: NutrientGroupEntity)
        fun onNutrientsTypeLoaded(nutrientGTEntity: NutrientListTypeEntity)
    }

    interface Presenter : Translatable.Presenter {
        fun deletePantry(idPurchase: String)
        fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity>
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String,
            language: String
        ): MutableLiveData<NutrientListTypeEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun deletePantry(id: String)
        fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity>
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String,
            language: String
        ): MutableLiveData<NutrientListTypeEntity>
    }
}