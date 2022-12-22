package com.myfood.mvp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.myfood.constants.Constant.Companion.FIELD_SIGN_UP
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.ActivityLoginBinding
import com.myfood.mvp.forgotpassword.ForgotPasswordActivity
import com.myfood.mvp.main.MainActivity
import com.myfood.mvp.signup.SignUpActivity
import com.myfood.popup.Popup


class LoginActivity : AppCompatActivity(), LoginContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivityLoginBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var loginModel: LoginModel
    private lateinit var loginPresenter: LoginPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var selectLanguage: String
    private var onLoad: Boolean = true

    //Metodo onCreate que se ejecuta cuando la vista esta creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Login
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos el modelo
        loginModel = LoginModel()

        //Creamos el presentador
        loginPresenter = LoginPresenter(this, loginModel, this)

        //Obtenemos el idioma de la App y establecemos las traducciones
        selectLanguage = loginPresenter.getCurrentLanguage()
        this.mutableTranslations = loginPresenter.getTranslations(selectLanguage.toInt())
        setTranslations()

        //Establecemos los lenguajes
        setLanguages(loginPresenter.getLanguages())

        //Inicializamos los botones
        initButtons()

    }

    override fun login() {

        //Capturamos los valores de los campos de texto
        val name = binding.etNameLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        //Llamamos al metodo para comprobar el login de usuario y contraseña
        loginPresenter.login(name, password).observe(this) { result ->

            //Si es erroneo mostramos un mensaje al usuario indicandolo
            if (result.status == com.myfood.constants.Constant.KO) {
                mutableTranslations?.get(com.myfood.constants.Constant.MSG_INCORRECT_LOGIN)?.let {
                    Popup.showInfo(
                        this,
                        resources,
                        it.text
                    )
                }
                //Si es correcto actualizamos el id de usuario de la App y vamos a la actividad
                //Main
            } else {
                loginModel.updateUserId(result.idUser)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setLanguages(languages: List<String>) {
        //Creamos un array y lo poblamos con los lenguajes
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)

        //Especificamos el tipo de layout que queremos para visualizar las opciones del spinner
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Asignamos el adapter al spinner
        binding.sLanguageLogin.adapter = spinnerAdapter

        //Inicializamos el evento que se ejecutara al seleccionar una opcion del combo
        initOnItemSelectedListener()

        //Seleccionamos el idioma actual en el spinner
        binding.sLanguageLogin.setSelection(selectLanguage.toInt() - 1)
    }

    override fun updateLanguage(position: Int) {

        val pos = position + 1

        //Actualizamos el idioma actual de la App con el nuevo
        loginPresenter.updateCurrentLanguage(pos.toString())

        //Seleccionamos el nuevo idioma en el combo
        binding.sLanguageLogin.setSelection(position)

        //Cargamos las traducciones para el nuevo idioma y las establecemos
        this.mutableTranslations = loginPresenter.getTranslations(pos)
        setTranslations()
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.etNameLogin.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_NAME)!!.text
        binding.etPasswordLogin.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_PASSWORD)!!.text
        binding.btnSignUpLogin.text = mutableTranslations?.get(FIELD_SIGN_UP)!!.text
        binding.btnPasswordForgotten.text =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_FORGOTTEN_PASSWORD)!!.text
        binding.btnLogin.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_LOGIN)!!.text
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
        binding.btnLogin.setOnClickListener { login() }
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