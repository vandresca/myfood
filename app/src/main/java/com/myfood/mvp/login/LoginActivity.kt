package com.myfood.mvp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.myfood.constants.Constant
import com.myfood.constants.Constant.Companion.FIELD_SIGN_UP
import com.myfood.databases.databasemysql.entity.LoginEntity
import com.myfood.databinding.ActivityLoginBinding
import com.myfood.mvp.forgotpassword.ForgotPasswordActivity
import com.myfood.mvp.main.MainActivity
import com.myfood.mvp.signup.SignUpActivity
import com.myfood.popup.Popup


class LoginActivity : AppCompatActivity(), LoginContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginModel: LoginModel
    private lateinit var loginPresenter: LoginPresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()
    private lateinit var selectLanguage: String
    private var onLoad: Boolean = true

    //Metodo onCreate que se ejecuta cuando la vista esta creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Login
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos el presentador
        loginPresenter = LoginPresenter(this)

        //Obtenemos las traducciones de pantalla
        mutableTranslations = loginPresenter.getTranslationsScreen()
        setTranslations()

        //Establecemos los lenguajes
        setLanguages()

        //Inicializamos los botones
        initButtons()

    }

    override fun onLogin(result: LoginEntity){

        //Si es erroneo mostramos un mensaje al usuario indicandolo
        if (result.status == Constant.KO) {
            Popup.showInfo(
                this,
                resources,
                mutableTranslations[Constant.MSG_INCORRECT_LOGIN]!!
            )
            //Si es correcto actualizamos el id de usuario de la App y vamos a la actividad
            //Main
        } else {
            loginPresenter.updateUserId(result.idUser)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLanguages() {

        //Asignamos el adapter al spinner
        binding.sLanguageLogin.adapter = loginPresenter.createAdapterLanguages()

        //Inicializamos el evento que se ejecutara al seleccionar una opcion del combo
        initOnItemSelectedListener()

        //Seleccionamos el idioma actual en el spinner
        binding.sLanguageLogin.setSelection(loginPresenter.getCurrentLanguage().toInt() - 1)
    }

    private fun updateLanguage(position: Int) {

        val pos = position + 1

        //Actualizamos el idioma actual de la App con el nuevo
        loginPresenter.setCurrentLanguage(pos.toString())

        //Seleccionamos el nuevo idioma en el combo
        binding.sLanguageLogin.setSelection(position)

        //Cargamos las traducciones para el nuevo idioma y las establecemos
        this.mutableTranslations = loginPresenter.getTranslationsScreen()
        setTranslations()
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.etNameLogin.hint = mutableTranslations[Constant.FIELD_USER_NAME]!!
        binding.etPasswordLogin.hint = mutableTranslations[Constant.FIELD_PASSWORD]!!
        binding.btnSignUpLogin.text = mutableTranslations[FIELD_SIGN_UP]!!
        binding.btnPasswordForgotten.text = mutableTranslations[Constant.FIELD_FORGOTTEN_PASSWORD]!!
        binding.btnLogin.text = mutableTranslations[Constant.BTN_LOGIN]!!
    }

    private fun initButtons() {

        //Inicializamos el click para el boton de ¿Olvido su contraseña? para que vaya a dicha
        //pantalla
        binding.btnPasswordForgotten.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //Inicializamos el click para el boton de Registro de Usuario para que vaya a dicha
        //pantalla
        binding.btnSignUpLogin.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //Inicializamos el click para el boton de login
        binding.btnLogin.setOnClickListener {
            //Capturamos los valores de los campos de texto
            val name = binding.etNameLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            //Llamamos al metodo para comprobar el login de usuario y contraseña
            loginPresenter.login(name, password)
        }
    }

    private fun initOnItemSelectedListener() {

        //Inicializamos el evento que se ejecutara al seleccionar una opción del spinner de
        //idioma
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
                    //Llamamos al metodo  updateLanguage para actualizar el idioma con la posicion
                    //del idioma seleccionado
                    updateLanguage(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}