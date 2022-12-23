package com.myfood.mvp.expiration

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ExpirationListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class ExpirationListModel : ExpirationListContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguage actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene el tipo de moneda actual de la App
    override fun getCurrentCurrency(): String {
        return myFoodRepository.getCurrentCurrency()
    }

    //Metodo que obtiene las traducciones para la pantalla de Caducidad
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.EXPIRATION.int)
    }

    //Metodo que obtiene el id de usuario actual de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene la lista de productos de despensa segun la caducidad indicada
    override fun getExpirationList(
        expiration: String,
        idUser: String
    ): MutableLiveData<ExpirationListEntity> {
        return myFoodRepository.getExpirationList(expiration, idUser)
    }

    //Metodo que elimina los productos caducados para un usuario concreto
    override fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.removeExpired(idUser)
    }
}