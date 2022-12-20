package com.example.myfood.mvp.signup


import androidx.lifecycle.MutableLiveData
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.OneValueEntity

class SignUpPresenter(
    private val signUpView: SignUpContract.View,
    private val signUpModel: SignUpContract.Model,
    signUpActivity: SignUpActivity
) : SignUpContract.Presenter {
    init {
        signUpModel.getInstance(signUpActivity)
    }

    override fun getCurrentLanguage(): String {
        return signUpModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = signUpModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String
    ): MutableLiveData<OneValueEntity> {
        return signUpModel.insertUser(name, surnames, email, password)
    }

    fun getMsgResult(
        name: String, surnames: String, email: String, password: String, confirmPassword: String,
        mutableTranslations: MutableMap<String, Translation>?
    ): String {
        var msg = ""
        val emailREGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
        if (name.isEmpty()) {
            msg = mutableTranslations?.get(Constant.MSG_NAME_IS_EMPTY)!!.text
        } else if (surnames.isEmpty()) {
            msg = mutableTranslations?.get(Constant.MSG_SURNAMES_IS_EMPTY)!!.text
        } else if (email.isEmpty()) {
            msg = mutableTranslations?.get(Constant.MSG_EMAIL_IS_EMPTY)!!.text
        } else if (!emailREGEX.toRegex().matches(email)) {
            msg = mutableTranslations?.get(Constant.MSG_EMAIL_FORMAT_INCORRECT)!!.text
        } else if (password.isEmpty()) {
            msg = mutableTranslations?.get(Constant.MSG_PASSWORD_IS_EMPTY)!!.text
        } else if (confirmPassword.isEmpty()) {
            msg = mutableTranslations?.get(Constant.MSG_CONFIRM_PASSWORD_IS_EMPTY)!!.text
        } else if (password != confirmPassword) {
            msg = mutableTranslations?.get(Constant.MSG_NOT_MATCH_PASSWORDS)!!.text
        }
        return msg
    }
}
