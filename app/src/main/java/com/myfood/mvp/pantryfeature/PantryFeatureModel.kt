package com.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryFeatureModel : PantryFeatureContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla de Caracteristicas de Producto
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    //Metodo que obtiene los atributos de un producto de despensa dada su id
    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return myFoodRepository.getPantryProduct(idPantry)
    }

    //Metodo que elimina un producto de despensa de la base de datos dado su id
    override fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.deletePantry(id)
    }
}