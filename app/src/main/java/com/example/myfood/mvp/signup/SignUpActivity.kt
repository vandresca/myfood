package com.example.myfood.mvp.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivitySignupBinding
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.popup.Popup

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpModel: SignUpModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUpModel = SignUpModel()
        binding.btnSignUp.setOnClickListener { signup() }

    }

    fun signup() {
        val name = binding.etNameSignUp.text.toString()
        val surnames = binding.etSurnamesSignUp.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPasswordSignup.text.toString()
        val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()

        var msg: String = ""
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (name.isEmpty()) {
            msg = "El campo nombre no puede ser vacio"
        } else if (surnames.isEmpty()) {
            msg = "El campo apellidos no puede ser vacio"
        } else if (email.isEmpty()) {
            msg = "El campo correo electrónico no puede ser vacio"
        } else if (!EMAIL_REGEX.toRegex().matches(email)) {
            msg = "Formato incorrecto de correo electrónico"
        } else if (password.isEmpty()) {
            msg = "El campo contraseña no puede ser vacio"
        } else if (confirmPassword.isEmpty()) {
            msg = "El campo confirmar contraseña no puede ser vacio"
        } else if (!password.equals(confirmPassword)) {
            msg = "Las contraseñas no coinciden"
        }

        if (!msg.isEmpty()) {
            Popup.showInfo(this, resources, msg)
        } else {
            signUpModel.insertUser(name, surnames, email, password) { result -> result(result) }
        }

    }

    private fun result(response: String?) {
        Popup.showInfo(this, resources, "¡Usuario insertado con exito!") { onClickOKButton() }

    }

    private fun onClickOKButton() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    fun onTranslationsLoaded(translations: List<Translation>) {

        /**
        translations.forEach {
        when (it.word) {
        "name" -> binding.etNameLogin.hint = it.text
        "password" -> binding.etPasswordLogin.hint = it.text
        "signUp" -> binding.btnSignUpLogin.text = it.text
        "forgotPass" -> binding.btnPasswordForgotten.text = it.text
        "login" -> binding.btnLogin.text = it.text

        }
        }
         **/
    }
}