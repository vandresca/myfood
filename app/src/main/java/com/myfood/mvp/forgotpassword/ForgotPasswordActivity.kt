package com.myfood.mvp.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myfood.constants.Constant
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databinding.ActivityForgotPasswordBinding
import com.myfood.mvp.login.LoginActivity
import com.myfood.popup.Popup

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivityForgotPasswordBinding
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()
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

        //Creamos el presentador
        forgotPasswordPresenter = ForgotPasswordPresenter(this)

        //Obtenemos las traducciones de pantalla
        mutableTranslations = forgotPasswordPresenter.getTranslationsScreen()
        setTranslations()

        //Inicializamos los botones
        initButtons()
    }

    private fun initButtons() {

        //Inicializamos el click del boton enviar enlace para resetear la contraseña
        binding.btnSendLink.setOnClickListener {
            val email = binding.etEmailForgotPass.text.toString()
            forgotPasswordPresenter.sendLink(email)
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
            mutableTranslations[Constant.TITLE_FORGOTTEN_PASSWORD]!!
        binding.tvForgotPasswordText.text =
            mutableTranslations[Constant.MSG_FORGOTTEN_PASSWORD_TEXT]!!
        binding.etEmailForgotPass.hint =
            mutableTranslations[Constant.FIELD_EMAIL]!!
        binding.btnSendLink.text =
            mutableTranslations[Constant.BTN_SEND_LINK_FORGOTTEN_PASSWORD]!!
    }

    //Metodo que se ejecuta una vez que se ha llamado al script para enviar el link
    override fun onSendLink(result: SimpleResponseEntity) {

        //Verificamos si la respuesta correcta y mostramos al usuario si se ha efectuado
        //correctamente o no
        if (result.status == com.myfood.constants.Constant.OK) {
            Popup.showInfo(
                this, resources,
                mutableTranslations[Constant.MSG_FORGOTTEN_PASSWORD_TEXT_OK]!!
            )
        } else {
            Popup.showInfo(
                this, resources,
                mutableTranslations[Constant.MSG_FORGOTTEN_PASSWORD_TEXT_KO]!!
            )
        }
    }
}