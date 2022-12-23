package com.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Característica Producto Despensa
interface PantryFeatureContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta tras eliminar el producto de despensa de la base de datos
        fun onDeletePantryProduct()

        //Metodo que se ejecuta al cargar un producto existente con la intención de
        //modificarlo
        fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que obtiene los atributos de un producto de despensa
        fun getPantryProduct(idPantry: String)

        //Metodo que elimina un producto de despensa de la base de datos
        fun deletePantry(idPurchase: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(context: Context)

        //Metodo que obtiene los atributos de un producto de despensa de la base de datos
        fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity>

        //Metodo que elimina un producto de despensa de la base de datos dado su id
        fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity>
    }
}