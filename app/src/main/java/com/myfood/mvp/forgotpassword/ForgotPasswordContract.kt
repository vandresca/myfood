package com.myfood.mvp.forgotpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// ¿Olvido su contraseña?
interface ForgotPasswordContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {

        //Metodo que se ejecuta despues de enviar el link para resetear la contraseña
        //de usuario
        fun onSendLink(result: SimpleResponseEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {

        //Metodo que envia un link para resetear la contraseña de usuario a partir del email
        //si existe en el idioma actual de la App
        fun sendLink(email: String)
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias del las bases de datos
        fun createInstances(context: Context)

        //Metodo que envia un link para resetear la contraseña de usuario en el idioma actual de la
        //App
        fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity>
    }
}