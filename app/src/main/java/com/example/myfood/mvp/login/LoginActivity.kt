package com.example.myfood.mvp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.FIELD_SIGN_UP
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityLoginBinding
import com.example.myfood.mvp.forgotpassword.ForgotPasswordActivity
import com.example.myfood.mvp.main.MainActivity
import com.example.myfood.mvp.signup.SignUpActivity
import com.example.myfood.popup.Popup


class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var loginModel: LoginModel
    private lateinit var loginPresenter: LoginPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var selectLanguage: String
    private var onLoad: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginModel = LoginModel()
        loginPresenter = LoginPresenter(this, loginModel, this)
        setLanguages(loginPresenter.getLanguages())
        initButtons()

    }

    override fun login() {
        val name = binding.etNameLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        loginPresenter.login(name, password).observe(this) { result ->
            if (result.status == Constant.KO) {
                mutableTranslations?.get(Constant.MSG_INCORRECT_LOGIN)?.let {
                    Popup.showInfo(
                        this,
                        resources,
                        it.text
                    )
                }
            } else {
                loginModel.updateUserId(result.idUser)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setLanguages(languages: List<String>) {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageLogin.adapter = spinnerAdapter
        initOnItemSelectedListener()
        selectLanguage = loginPresenter.getCurrentLanguage()
        binding.sLanguageLogin.setSelection(selectLanguage.toInt() - 1)
        this.mutableTranslations = loginPresenter.getTranslations(selectLanguage.toInt())
        setTranslations()
    }

    override fun updateLanguage(position: Int) {
        val pos = position + 1
        loginPresenter.updateCurrentLanguage(pos.toString())
        binding.sLanguageLogin.setSelection(position)
        this.mutableTranslations = loginPresenter.getTranslations(pos)
        setTranslations()
    }

    override fun setTranslations() {
        binding.etNameLogin.hint = mutableTranslations?.get(Constant.FIELD_NAME)!!.text
        binding.etPasswordLogin.hint = mutableTranslations?.get(Constant.FIELD_PASSWORD)!!.text
        binding.btnSignUpLogin.text = mutableTranslations?.get(FIELD_SIGN_UP)!!.text
        binding.btnPasswordForgotten.text =
            mutableTranslations?.get(Constant.FIELD_FORGOTTEN_PASSWORD)!!.text
        binding.btnLogin.text = mutableTranslations?.get(Constant.BTN_LOGIN)!!.text
    }

    private fun initButtons() {
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

    private fun initOnItemSelectedListener() {
        binding.sLanguageLogin.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (onLoad) {
                        onLoad = false
                        return
                    }
                    updateLanguage(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}