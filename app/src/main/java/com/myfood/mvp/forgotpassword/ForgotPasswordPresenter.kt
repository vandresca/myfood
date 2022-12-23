package com.myfood.mvp.forgotpassword

class ForgotPasswordPresenter(
    private val forgotPasswordActivity: ForgotPasswordActivity,
) : ForgotPasswordContract.Presenter {

    //Declaración de variables globales
    private var forgotPasswordModel: ForgotPasswordModel= ForgotPasswordModel()
    private var currentLanguage:String

    init {

        //Creamos las instancias de las bases de datos
        forgotPasswordModel.createInstances(forgotPasswordActivity)

        //Obtenemos el lenguaje actual de la App
        currentLanguage = forgotPasswordModel.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = forgotPasswordModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que envia en el idioma actual de la App un email al usuario para que resetee
    //la contraseña
    override fun sendLink(email: String){
        forgotPasswordModel.sendLink(currentLanguage, email).
        observe(forgotPasswordActivity)
        { response -> forgotPasswordActivity.onSendLink(response) }
    }
}