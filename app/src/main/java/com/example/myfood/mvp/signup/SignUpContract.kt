package com.example.myfood.mvp.signup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

interface SignUpContract {
    interface View : Translatable.View {
        fun onInsertedUser(result: SimpleResponseEntity)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String
        ): MutableLiveData<SimpleResponseEntity>
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String,
        ): MutableLiveData<SimpleResponseEntity>
    }
}