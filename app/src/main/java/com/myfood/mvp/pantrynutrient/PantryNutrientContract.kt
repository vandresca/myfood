package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.NutrientGroupEntity
import com.myfood.databases.databasemysql.entity.NutrientListTypeEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Nutrientes de Producto Despensa
interface PantryNutrientContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta una vez que obtenemos el grupo de tipo de nutrientes de
        //los elementos
        fun onNutrientsLoaded(nutrientGEntity: NutrientGroupEntity)

        //Metodo que se ejecuta una vez que obtenemos los nutrientes del alimento para el
        //tipo especificado
        fun onNutrientsTypeLoaded(nutrientGTEntity: NutrientListTypeEntity)
    }

    interface Presenter : Translatable.Presenter {

        //Metodo que elimina el producto de despensa de la base de datos dado su id
        fun deletePantry(idPurchase: String)

        //Metodo que obtiene el grupo de tipo de nutrientes de los alimentos
        fun getNutrients(language: String)

        //Metood que obtiene los nutrientes del alimento para un tipo de nutriente
        //especificado
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String
        )
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun createInstances(context: Context)
        fun deletePantry(id: String)
        fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity>
        fun getNutrientsByType(
            typeNutrient: String,
            idFood: String,
            language: String
        ): MutableLiveData<NutrientListTypeEntity>
    }
}