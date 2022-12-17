package com.example.myfood.mvp.forgotpassword

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

interface ForgotPasswordContract {
    interface View : Translatable.View {
        fun onSendLink(result: SimpleResponseEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity>
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity>
    }
}