package com.example.myfood.mvp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityLoginBinding
import com.example.myfood.mvp.forgotpassword.ForgotPasswordActivity
import com.example.myfood.mvp.main.MainActivity
import com.example.myfood.mvp.signup.SignUpActivity
import com.example.myfood.popup.Popup
import org.json.JSONObject


class LoginActivity : AppCompatActivity(), LoginContract.View {
    companion object {
        private const val INCORRECT_LOGIN = "KO"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var loginModel: LoginModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginModel = LoginModel()
        loginModel.getInstance(this)
        loginModel.getLanguages(this)
        loginModel.getTranslations(this)

        binding.btnPasswordForgotten.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUpLogin.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        val name = binding.etNameLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        loginModel.login(name, password) { response -> onLogged(response) }
    }

    private fun onLogged(result: String?) {
        val response = JSONObject(result).get("response")
        if (response == INCORRECT_LOGIN) {
            Popup.showInfo(this, resources, "Nombre de usuario o contraseña incorrecto")
        } else {
            loginModel.updateUserId(response.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        translations.forEach {
            when (it.word) {
                "name" -> binding.etNameLogin.hint = it.text
                "password" -> binding.etPasswordLogin.hint = it.text
                "signUp" -> binding.btnSignUpLogin.text = it.text
                "forgotPass" -> binding.btnPasswordForgotten.text = it.text
                "login" -> binding.btnLogin.text = it.text

            }
        }
    }

    override fun onCurrentLanguageLoaded(language: String) {
        binding.sLanguageLogin.setSelection(Integer.parseInt(language) - 1)
    }

    override fun onLanguagesLoaded(languages: List<String>) {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageLogin.adapter = spinnerAdapter
        loginModel.getCurrentLanguage(this)
        initOnItemSelectedListener()
    }

    override fun updateLanguage(position: Int) {
        val pos = position + 1
        loginModel.getTranslations(this, pos)
        loginModel.updateCurrencyLanguage(pos.toString())
    }

    fun initOnItemSelectedListener() {
        binding.sLanguageLogin.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updateLanguage(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}