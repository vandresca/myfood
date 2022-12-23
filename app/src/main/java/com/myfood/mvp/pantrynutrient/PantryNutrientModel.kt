package com.example.myfood.mvp.pantryfeature

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.MyFoodRepository
import com.myfood.databases.databasemysql.entity.NutrientGroupEntity
import com.myfood.databases.databasemysql.entity.NutrientListTypeEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryNutrientModel : PantryNutrientContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el idioma actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla Nutrientes Producto Despensa
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    //Metodo que elimina el producto despensa de la base de datos dado su id
    override fun deletePantry(id: String) {
        myFoodRepository.deletePantry(id)
    }

    //Metodo que obtiene el grupo de tipo de nutrientes de los alimentos
    override fun getNutrients(language: String): MutableLiveData<NutrientGroupEntity> {
        return myFoodRepository.getNutrients(language)
    }

    //Metodo que obtiene los nutrientes de un alimento de un tipo
    override fun getNutrientsByType(typeNutrient: String, idFood: String, language: String):
            MutableLiveData<NutrientListTypeEntity> {
        return myFoodRepository.getNutrientsByType(typeNutrient, idFood, language)
    }
}