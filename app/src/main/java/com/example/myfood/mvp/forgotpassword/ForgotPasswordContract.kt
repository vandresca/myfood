package com.example.myfood.mvp.forgotpassword

import com.example.myfood.interfaces.Translatable

interface ForgotPasswordContract {
    interface View : Translatable.View
    interface Model : Translatable.Model {
        fun getInstance(application: ForgotPasswordActivity)
    }
}