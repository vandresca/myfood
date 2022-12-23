package com.myfood.mvp.login


import android.R
import android.widget.ArrayAdapter

class LoginPresenter(
    private val loginActivity: LoginActivity
) : LoginContract.Presenter {

    //Declaración de las variables globales
    private val loginModel: LoginModel = LoginModel()
    private val languages: List<String>
    private var currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        loginModel.createInstances(loginActivity)

        //Obtenemos los idiomas disponibles en la App
        languages = loginModel.getLanguages()

        //Obtenemos el idioma actual de la App
        currentLanguage = loginModel.getCurrentLanguage()
    }

    fun createAdapterLanguages(): ArrayAdapter<String>{
        //Creamos un array y lo poblamos con los lenguajes
        val adapter = ArrayAdapter(loginActivity, R.layout.simple_spinner_item, languages)

        //Especificamos el tipo de layout que queremos para visualizar las opciones del spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        return adapter
    }

    //Hacemos login con un usuario y contraseña y devolvemos el resultado en el metodo
    //onLogin
    override fun login(name: String, password: String) {
        loginModel.login(name, password).
        observe(loginActivity) {result -> loginActivity.onLogin(result)}
    }

    //Actualizamos el lenguage actual de la App
    fun setCurrentLanguage(language: String){
        currentLanguage = language
        loginModel.updateCurrentLanguage(currentLanguage)
    }

    //Obtenemos el idioma actual de la App
    fun getCurrentLanguage():String{
        return currentLanguage
    }

    //Metodo que obtiene las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = loginModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que actualiza el id de usuario actual de la App
    override fun updateUserId(userId: String) {
        loginModel.updateUserId(userId)
    }
}
