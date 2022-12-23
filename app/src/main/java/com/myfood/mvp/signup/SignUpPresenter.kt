package com.myfood.mvp.signup


import com.myfood.constants.Constant

class SignUpPresenter(
    private val signUpActivity: SignUpActivity
) : SignUpContract.Presenter {

    //Declaración de variables globales
    private val signUpModel: SignUpModel = SignUpModel()
    private val currentLanguage:String

    init {

        //Creamos las instancias de las bases de datos
        signUpModel.createInstances(signUpActivity)

        //Obtenemos el idioma actual de la App
        currentLanguage = signUpModel.getCurrentLanguage()
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = signUpModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que inserta un usuario nuevo en la base de datos
    override fun insertUser(
        name: String,
        surnames: String,
        email: String,
        password: String
    ){
        signUpModel.insertUser(name, surnames, email, password).
        observe(signUpActivity)
        { result -> signUpActivity.onInsertedUser(result) }
    }

    //Metodo que comprueba dados los datos de inserción de usuario que sean correctos
    fun getMsgResult(
        name: String, surnames: String, email: String, password: String, confirmPassword: String,
        mutableTranslations: MutableMap<String, String>
    ): String {
        var msg = ""
        val emailREGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
        if (name.isEmpty()) {
            msg = mutableTranslations[Constant.MSG_NAME_IS_EMPTY]!!
        } else if (surnames.isEmpty()) {
            msg =
                mutableTranslations[Constant.MSG_SURNAMES_IS_EMPTY]!!
        } else if (email.isEmpty()) {
            msg = mutableTranslations[Constant.MSG_EMAIL_IS_EMPTY]!!
        } else if (!emailREGEX.toRegex().matches(email)) {
            msg =
                mutableTranslations[Constant.MSG_EMAIL_FORMAT_INCORRECT]!!
        } else if (password.isEmpty()) {
            msg =
                mutableTranslations[Constant.MSG_PASSWORD_IS_EMPTY]!!
        } else if (confirmPassword.isEmpty()) {
            msg =
                mutableTranslations[Constant.MSG_CONFIRM_PASSWORD_IS_EMPTY]!!
        } else if (password != confirmPassword) {
            msg =
                mutableTranslations[Constant.MSG_NOT_MATCH_PASSWORDS]!!
        }
        return msg
    }
}
