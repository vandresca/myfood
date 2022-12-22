package com.myfood.mvp.forgotpassword

import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation

class ForgotPasswordPresenter(
    private val forgotPasswordView: ForgotPasswordContract.View,
    private val forgotPasswordModel: ForgotPasswordContract.Model,
    forgotPasswordActivity: ForgotPasswordActivity
) : ForgotPasswordContract.Presenter {
    init {
        forgotPasswordModel.getInstance(forgotPasswordActivity)
    }

    override fun getCurrentLanguage(): String {
        return forgotPasswordModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = forgotPasswordModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun sendLink(language: String, email: String): MutableLiveData<SimpleResponseEntity> {
        return forgotPasswordModel.sendLink(language, email)
    }
}