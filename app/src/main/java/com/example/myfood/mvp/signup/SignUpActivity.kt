package com.example.myfood.mvp.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivitySignupBinding
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.popup.Popup

class SignUpActivity : AppCompatActivity(), SignUpContract.View {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpModel: SignUpModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutSignUp.visibility = View.INVISIBLE
        signUpModel = SignUpModel()
        signUpModel.getInstance(this)
        signUpModel.getCurrentLanguage(this) { currentLanguage ->
            onCurrentLanguageLoaded(
                currentLanguage
            )
        }
        binding.btnSignUp.setOnClickListener { signup() }

    }

    fun signup() {
        val name = binding.etNameSignUp.text.toString()
        val surnames = binding.etSurnamesSignUp.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPasswordSignup.text.toString()
        val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()

        var msg = ""
        val emailREGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
        if (name.isEmpty()) {
            msg = mutableTranslations[Constant.NAME_IS_EMPTY]!!.text
        } else if (surnames.isEmpty()) {
            msg = mutableTranslations[Constant.SURNAMES_IS_EMPTY]!!.text
        } else if (email.isEmpty()) {
            msg = mutableTranslations[Constant.EMAIL_IS_EMPTY]!!.text
        } else if (!emailREGEX.toRegex().matches(email)) {
            msg = mutableTranslations[Constant.EMAIL_FORMAT_INCORRECT]!!.text
        } else if (password.isEmpty()) {
            msg = mutableTranslations[Constant.PASSWORD_IS_EMPTY]!!.text
        } else if (confirmPassword.isEmpty()) {
            msg = mutableTranslations[Constant.CONFIRM_PASSWORD_IS_EMPTY]!!.text
        } else if (password != confirmPassword) {
            msg = mutableTranslations[Constant.NOT_MATCH_PASSWORDS]!!.text
        }

        if (msg.isNotEmpty()) {
            Popup.showInfo(this, resources, msg)
        } else {
            signUpModel.insertUser(name, surnames, email, password) { result() }
        }

    }

    private fun result() {
        Popup.showInfo(
            this,
            resources,
            mutableTranslations[Constant.USER_INSERTED]!!.text
        ) { onClickOKButton() }
    }

    private fun onClickOKButton() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf()
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        setTranslations()
    }

    private fun setTranslations() {
        binding.layoutSignUp.visibility = View.VISIBLE
        binding.etConfirmPasswordSignUp.hint = mutableTranslations[Constant.CONFIRM_PASSWORD]?.text
        binding.etEmailSignUp.hint = mutableTranslations[Constant.EMAIL]?.text
        binding.etSurnamesSignUp.hint = mutableTranslations[Constant.SURNAMES]?.text
        binding.etNameSignUp.hint = mutableTranslations[Constant.NAME]?.text
        binding.etPasswordSignup.hint = mutableTranslations[Constant.PASSWORD]?.text
        binding.btnSignUp.text = mutableTranslations[Constant.SIGN_UP]?.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        signUpModel.getTranslations(this, language.toInt()) { translations ->
            onTranslationsLoaded(
                translations
            )
        }
    }
}