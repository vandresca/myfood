package com.myfood.mvp.pantrylist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class PantryListModel : PantryListContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el idioma actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene el tipo de moneda acutal de la App
    override fun getCurrentCurrency(): String {
        return myFoodRepository.getCurrentCurrency()
    }

    //Metodo que obtiene las traducciones para la pantalla de Despensa
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    //Metodo que obtiene el id de usuario de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene la lista de productos de despensa del usuario actual
    override fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {
        return myFoodRepository.getPantryList(idUser)
    }

    //Metodo que elimina un producto despensa de la base de datos a partir de su id
    override fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.deletePantry(id)
    }
}