package com.myfood.mvp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.LoginEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class LoginModel : LoginContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene los idiomas disponibles en la App
    override fun getLanguages(): List<String> {
        return myFoodRepository.getLanguages()
    }

    //Metodo que obtiene las traducciones para la pantalla de Login
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.LOGIN.int)
    }

    //Metodo que obtiene el lenguage actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que actualiza el lenguage actual de la App
    override fun updateCurrentLanguage(language: String) {
        myFoodRepository.updateCurrentLanguage(language)
    }

    //Metodo que actualiza el id de usuario actual de la App
    override fun updateUserId(userId: String) {
        myFoodRepository.updateUserId(userId)
    }

    //Metodo que checkea un login con un nombre de usuario y contraseña
    override fun login(name: String, password: String): MutableLiveData<LoginEntity> {
        return myFoodRepository.login(name, password)
    }
}
