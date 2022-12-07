package com.example.myfood.mvp.signup

import android.content.Context
import com.example.myfood.interfaces.Translatable

interface SignUpContract {
    interface View : Translatable.View
    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String,
            callback: (String?) -> Unit
        )
    }
}