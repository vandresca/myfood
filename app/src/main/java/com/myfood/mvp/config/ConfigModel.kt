package com.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class ConfigModel : ConfigContract.Model {

    //Declaramos una instancia del repositorio de metodos de acceso a la base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene las traducciones del menu de navegacion
    override fun getTranslationsMenu(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_LIST.int)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla de Configuración
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }

    //Metodo que obtiene los idiomas disponibles en la App
    override fun getLanguages(): List<String> {
        return myFoodRepository.getLanguages()
    }

    //Metodo que obtiene los tipos de moneda del lenguaje actual de la App
    override fun getCurrencies(language: Int): List<String> {
        return myFoodRepository.getCurrencies(language)
    }

    //Metodo que obtiene el id de usuario actual de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene el tipo de moneda actual de la App
    override fun getCurrentCurrency(): String {
        return myFoodRepository.getCurrentCurrency()
    }

    //Metodo que actualiza el lenguage actual de la App
    override fun updateCurrentLanguage(language: String) {
        myFoodRepository.updateCurrentLanguage(language)
    }

    //Metodo que actualiza el tipo de moneda actual de la App
    override fun updateCurrentCurrency(currency: String) {
        myFoodRepository.updateCurrentCurrency(currency)
    }

    //Metodo que actualiza el correo del usuario actual de la App
    override fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.changeEmail(email, user)
    }

    //Metodo que obtiene el correo del usuario actual de la App
    override fun getEmail(user: String): MutableLiveData<OneValueEntity> {
        return myFoodRepository.getEmail(user)
    }

    //Metodo que actualiza la contraseña del usuario actual de la App
    override fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.changePassword(password, user)
    }

    //Metodo que obtiene la contraseña del usuario actual de la App
    override fun getPassword(user: String): MutableLiveData<OneValueEntity> {
        return myFoodRepository.getPassword(user)
    }
}