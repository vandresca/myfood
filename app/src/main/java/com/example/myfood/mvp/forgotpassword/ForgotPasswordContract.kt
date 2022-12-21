package com.example.myfood.mvp.forgotpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// ¿Olvido su contraseña?
interface ForgotPasswordContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onSendLink(result: SimpleResponseEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity>
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity>
    }
}