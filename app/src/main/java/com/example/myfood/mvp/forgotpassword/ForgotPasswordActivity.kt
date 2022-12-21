package com.example.myfood.mvp.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityForgotPasswordBinding
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.popup.Popup

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var forgotPasswordModel: ForgotPasswordModel
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var idLanguage: String
    private lateinit var forgotPasswordPresenter: ForgotPasswordPresenter

    //Metodo onCreate que se ejecuta cuando la vista esta creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //¿Olvido su contraseña?
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutForgotPassword.visibility = View.INVISIBLE

        //Creamos el modelo
        forgotPasswordModel = ForgotPasswordModel()

        //Creamos el presentador
        forgotPasswordPresenter = ForgotPasswordPresenter(this, forgotPasswordModel, this)

        //Obtenemos el idioma de la App y establecemos las traducciones
        idLanguage = forgotPasswordPresenter.getCurrentLanguage()
        this.mutableTranslations = forgotPasswordPresenter.getTranslations(idLanguage.toInt())
        setTranslations()

        //Inicializamos los botones
        initButtons()
    }

    private fun initButtons() {

        //Inicializamos el click del boton enviar enlace para resetear la contraseña
        binding.btnSendLink.setOnClickListener {
            val email = binding.etEmailForgotPass.text.toString()
            forgotPasswordPresenter.sendLink(idLanguage, email).observe(this)
            { response -> onSendLink(response) }
        }

        //Inicializamos el click para el boton volver a la pantalla de Login
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //Establecemos la pantalla de traducciones
    override fun setTranslations() {
        binding.layoutForgotPassword.visibility = View.VISIBLE
        binding.tvForgotPasswordTitle.text =
            mutableTranslations?.get(Constant.TITLE_FORGOTTEN_PASSWORD)!!.text
        binding.tvForgotPasswordText.text =
            mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT)!!.text
        binding.etEmailForgotPass.hint = mutableTranslations?.get(Constant.FIELD_EMAIL)!!.text
        binding.btnSendLink.text =
            mutableTranslations?.get(Constant.BTN_SEND_LINK_FORGOTTEN_PASSWORD)!!.text
    }

    //Metodo que se ejecuta una vez que se ha llamado al script para enviar el link
    override fun onSendLink(result: SimpleResponseEntity) {

        //Verificamos si la respuesta correcta y mostramos al usuario si se ha efectuado
        //correctamente o no
        if (result.status == Constant.OK) {
            Popup.showInfo(
                this, resources,
                mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT_OK)!!.text
            )
        } else {
            Popup.showInfo(
                this, resources,
                mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT_KO)!!.text
            )
        }
    }
}