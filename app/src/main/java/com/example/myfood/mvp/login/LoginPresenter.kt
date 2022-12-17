package com.example.myfood.mvp.login


import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.LoginEntity

class LoginPresenter(
    private val loginView: LoginContract.View,
    private val loginModel: LoginContract.Model,
    loginActivity: LoginActivity
) : LoginContract.Presenter {
    init {
        loginModel.getInstance(loginActivity)
    }

    override fun login(name: String, password: String): MutableLiveData<LoginEntity> {
        return loginModel.login(name, password)
    }

    override fun getCurrentLanguage(): String {
        return loginModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = loginModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }


    override fun getLanguages(): List<String> {
        return loginModel.getLanguages()
    }

    override fun updateCurrentLanguage(language: String) {
        loginModel.updateCurrentLanguage(language)
    }

    override fun updateUserId(userId: String) {
        loginModel.updateUserId(userId)
    }
}
