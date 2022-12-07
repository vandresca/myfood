package com.example.myfood.mvp.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var forgotPasswordModel: ForgotPasswordModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutForgotPassword.visibility = View.INVISIBLE
        forgotPasswordModel = ForgotPasswordModel()
        forgotPasswordModel.getInstance(this)
        forgotPasswordModel.getCurrentLanguage(this)
        { currentLanguage -> onCurrentLanguageLoaded(currentLanguage) }
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf()
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        setTranslations()
    }

    private fun setTranslations() {
        binding.layoutForgotPassword.visibility = View.VISIBLE
        binding.tvForgotPasswordTitle.text =
            mutableTranslations[Constant.FORGOTTEN_PASSWORD_TITLE]!!.text
        binding.tvForgotPasswordText.text =
            mutableTranslations[Constant.FORGOTTEN_PASSWORD_TEXT]!!.text
        binding.etEmailForgotPass.hint = mutableTranslations[Constant.EMAIL]!!.text
        binding.btnSendLink.text =
            mutableTranslations[Constant.BTN_SEND_LINK_FORGOTTEN_PASSWORD]!!.text
        //"forgotPasswordSended"->{}
    }

    override fun onCurrentLanguageLoaded(language: String) {
        forgotPasswordModel.getTranslations(this, language.toInt())
        { translations -> onTranslationsLoaded(translations) }
    }
}