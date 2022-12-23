package com.myfood.mvp.config

import android.R
import android.content.Context
import android.widget.ArrayAdapter

class ConfigPresenter(
    private val configFragment: ConfigFragment,
    private val context: Context
) : ConfigContract.Presenter {

    //Declaramos las variables globales
    private var configModel: ConfigModel = ConfigModel()
    private var userId:String
    private var languages: List<String>
    private var currentLanguage:String
    private var currentCurrency: String
    private var currencies: List<String>



    init {

        //Creamos las instancias de la base de datos
        configModel.createInstances(context)

        //Obtenemos el usuario de la App
        userId = configModel.getUserId()

        //Obtenemos el grupo de idiomas disponible
        languages = configModel.getLanguages()

        //Obtenemos el idioma actual de la App
        currentLanguage = configModel.getCurrentLanguage()

        //Obtenemos los tipos de moneda del idioma actual de la App
        currencies = configModel.getCurrencies(currentLanguage.toInt())

        //Obtenemos el tipo de moneda actual de la App
        currentCurrency = configModel.getCurrentCurrency()
    }

    //Metodo que obtiene la posición del lenguage en la lista de lenguages
    fun getPositionLanguages(language: String): Int{
        return languages.indexOf(language)
    }

    //Metodo que obtiene la posició del tipo de moneda en la lista de tipo de monedas
    fun getPositionCurrencies(currency: String): Int{
        return currencies.indexOf(currency)
    }

    //Metodo que crea un adapter para el spinner de lenguages
    fun createAdapterLanguages(): ArrayAdapter<String>{
        //Creamos un adapter y poblamos el combo con los lenguajes
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, languages)

        //Establecemos el layout que se mostrara cuando se muestre las opciones de selección
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        return adapter
    }

    //Metodo que crea un adapter para el spinner de tipo de moneda
    fun createAdapterCurrencies(): ArrayAdapter<String>{

        //
        currencies = configModel.getCurrencies(currentLanguage.toInt())

        //Creamos un adapter y poblamos el combo con los tipos de moneda
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, currencies)

        //Establecemos el layout que se mostrara cuando se muestre las opciones de selección
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        return adapter
    }

    //Metodo que obtiene las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = configModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que obtiene las traducciones del menu de navegación
    override fun getTranslationsMenu(): MutableMap<String, String> {
        val mutableTranslations: MutableMap<String, String> = mutableMapOf()
        val translations = configModel.getTranslationsMenu(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que obtiene los lenguages disponibles en la App
    override fun getLanguages(): List<String> {
        return configModel.getLanguages()
    }

    //Metodo que obtiene el lenguage actual de la App
    fun getCurrentLanguage(): Int{
        return currentLanguage.toInt()
    }

    //Metodo que establece el lenguage actual en la App
    fun setCurrentLanguage(language: String) {
        currentLanguage = language
        configModel.updateCurrentLanguage(currentLanguage)
    }

    //Metodo que establece el tipo de moneda actual en la App
    fun setCurrentCurrency(currency: String){
        currencies = configModel.getCurrencies(currentLanguage.toInt())
        configModel.updateCurrentCurrency(currency)
    }

    //Metodo que obtiene el tipo de moneda actual
    fun getCurrentCurrency(): String{
        return currentCurrency
    }

    override fun getEmail(){
        configModel.getEmail(userId).
        observe(configFragment)
        { response -> configFragment.onGottenEmail(response) }
    }

    override fun getPassword(){
        configModel.getPassword(userId).
        observe(configFragment)
        { response -> configFragment.onGottenPassword(response) }
    }

    override fun changeEmail(email: String){
        configModel.changeEmail(email, userId).
        observe(configFragment)
        { response -> configFragment.onChangeEmail(response) }
    }

    override fun changePassword(password: String){
        configModel.changePassword(password, userId).
        observe(configFragment)
        { response -> configFragment.onChangePassword(response) }
    }

    override fun updateCurrentLanguage(language: String) {
        configModel.updateCurrentLanguage(language)
    }

    override fun updateCurrentCurrency(currency: String) {
        configModel.updateCurrentCurrency(currency)
    }

}