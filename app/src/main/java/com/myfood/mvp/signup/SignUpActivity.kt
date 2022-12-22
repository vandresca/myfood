package com.myfood.mvp.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myfood.constants.Constant.Companion.FIELD_SIGN_UP
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.ActivitySignupBinding
import com.myfood.mvp.login.LoginActivity
import com.myfood.popup.Popup

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpModel: SignUpModel
    private lateinit var signUpPresenter: SignUpPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Metodo onCreate que se ejecuta cuando la vista esta creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Registro de usuario
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutSignUp.visibility = View.INVISIBLE

        //Creamos el modelo
        signUpModel = SignUpModel()

        //Creamos el presentador
        signUpPresenter = SignUpPresenter(this, signUpModel, this)

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = signUpPresenter.getCurrentLanguage()
        this.mutableTranslations = signUpPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Inicializamos el click del boton Registrarse
        binding.btnSignUp.setOnClickListener { signup() }

        //Inicializamos el click del boton volver, para que vuelva a la pantalla de login
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun signup() {

        //Capturamos los valores de los campos de texto
        val name = binding.etNameSignUp.text.toString()
        val surnames = binding.etSurnamesSignUp.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPasswordSignup.text.toString()
        val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()

        //Llamamos con los valores al metod getResult que nos devuelve un string vacio si
        //todo es correcto y si no un mensaje indicando el problema
        val msg = signUpPresenter.getMsgResult(
            name, surnames, email, password, confirmPassword,
            mutableTranslations
        )
        //Si el mensaje no es vacio mostramos al usuario el problema
        if (msg.isNotEmpty()) {
            Popup.showInfo(this, resources, msg)
        } else {
            //Si el mensaje es vacio insertamos el usuario en la base de datos MySQL
            signUpModel.insertUser(name, surnames, email, password).observe(this)
            { result -> onInsertedUser(result) }
        }

    }

    override fun onInsertedUser(result: OneValueEntity) {

        //Verificamos que el resultado es correcto
        if (result.status == com.myfood.constants.Constant.OK) {

            //Si el resultado es correcto mostramos un mensaje indicando que todo ha ido
            // correctamente y volvemos a la pantalla de Login
            Popup.showInfo(
                this,
                resources,
                mutableTranslations?.get(com.myfood.constants.Constant.MSG_USER_INSERTED)!!.text
            ) { onClickOKButton() }
        }
    }

    private fun onClickOKButton() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    override fun setTranslations() {
        binding.layoutSignUp.visibility = View.VISIBLE
        binding.etConfirmPasswordSignUp.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_CONFIRM_PASSWORD)!!.text
        binding.etEmailSignUp.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_EMAIL)!!.text
        binding.etSurnamesSignUp.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SURNAMES)!!.text
        binding.etNameSignUp.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_NAME)!!.text
        binding.etPasswordSignup.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_PASSWORD)!!.text
        binding.btnSignUp.text = mutableTranslations?.get(FIELD_SIGN_UP)!!.text
    }
}