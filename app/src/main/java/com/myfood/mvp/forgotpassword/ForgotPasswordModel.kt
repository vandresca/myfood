package com.myfood.mvp.forgotpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class ForgotPasswordModel : ForgotPasswordContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla de ¿Olvido la contraseña?
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.YOU_FORGOT_THE_PASSWORD.int)
    }

    //Metodo que envia un link para resetear la contraseña de usuario en el idioma actual de la App
    override fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.sendLink(language, email)
    }
}