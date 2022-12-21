package com.example.myfood.mvp.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.databinding.ConfigFragmentBinding
import com.example.myfood.mvp.quantityunitlist.QuantityUnitListFragment
import com.example.myfood.mvp.storeplacelist.StorePlaceListFragment
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.popup.Popup

class ConfigFragment(private var activityMainBinding: ActivityMainBinding) : Fragment(),
    ConfigContract.View {

    //Declaración variables globales
    private var _binding: ConfigFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var spinnerAdapterLanguage: ArrayAdapter<String>
    private lateinit var spinnerAdapterCurrency: ArrayAdapter<String>
    private lateinit var configModel: ConfigModel
    private lateinit var idUser: String
    private lateinit var languages: List<String>
    private lateinit var currencies: List<String>
    private var languageSelected: Int = 0
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var configPresenter: ConfigPresenter

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Configuración
        _binding = ConfigFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutConfig.visibility = View.INVISIBLE

        //Creamos el modelo
        configModel = ConfigModel()

        //Creamos el presentador
        configPresenter = ConfigPresenter(this, configModel, requireContext())

        //Inicializamos el combo lenguajes
        initLanguages()

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = configPresenter.getCurrentLanguage()
        this.mutableTranslations = configPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Obtenemos usuario y datos relacionados
        initUser()

        //Inicializamos los clicks de los botones
        initClickButtons()
    }

    private fun initLanguages() {
        //Obtenemos todos los lenguages de la App
        val languages = configPresenter.getLanguages()

        //Los almacenamos en una variable global
        this.languages = languages

        //Creamos un adapter y poblamos el combo con los lenguajes
        spinnerAdapterLanguage =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        spinnerAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Añadimos el adapter al spinner
        binding.sLanguageConfig.adapter = spinnerAdapterLanguage

        //Obtenemos el lenguaje actual de la App
        val currentLanguage = configPresenter.getCurrentLanguage()

        //Seleccionamos en el combo el lenguaje actual
        binding.sLanguageConfig.setSelection(currentLanguage.toInt() - 1)

        //Inicializamos el evento que se produce al seleccionar una opción del combo
        initOnItemSelectedListenerLanguage()
    }

    private fun initUser() {
        //Obtenemos el usuario de la App
        val idUser = configPresenter.getUserId()

        //Lo almacenamos en una variable global
        this.idUser = idUser

        //Obtenemos el email del usuario
        configPresenter.getEmail(idUser)
            .observe(this.viewLifecycleOwner) { response -> onGottenEmail(response) }

        //Obtenemos el password del usuario
        configPresenter.getPassword(idUser)
            .observe(this.viewLifecycleOwner) { response -> onGottenPassword(response) }
    }

    private fun initClickButtons() {

        //Inicializamos el click en el boton cambiar email
        binding.btnChangeEmail.setOnClickListener {

            //Recuperamos el valor del campo de texto
            val email = binding.etConfigEmail.text.toString()

            //Verificamos mediante una expresión regular que el correo tenga
            //un formato correcto
            val emailREGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"

            //Si el correo es vacio mostramos un mensaje al usuario indicandolo
            if (email.isEmpty()) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_EMAIL_REQUIRED_CONF)!!.text
                )
                //Si el formato de correo es incorrecto mostramos un menaje al usuario indicandolo
            } else if (!emailREGEX.toRegex().matches(email)) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_EMAIL_FORMAT_INCORRECT_CONF)!!.text
                )
                //Si todo es correcto modificamos el correo antiguo por el nuevo para ese usuario
            } else {
                configPresenter.changeEmail(email, idUser).observe(this.viewLifecycleOwner)
                { response -> onChangeEmail(response) }
            }
        }

        //Inicializamos el click para el boton  cambiar contraseña
        binding.btnChangePassword.setOnClickListener {
            //Recuperamos el valor del campo de texto
            val password = binding.etConfigPassword.text.toString()

            //Cambiamos la contraseña
            configPresenter.changePassword(password, idUser).observe(this.viewLifecycleOwner)
            { response -> onChangePassword(response) }
        }

        //Inicializamos el click para el boton cambiar lenguage y tipo de moneda
        binding.btnChangeLangAndCurrency.setOnClickListener {

            //Actualizamos el lenguaje en la App
            configPresenter.updateCurrentLanguage((languages.indexOf(binding.sLanguageConfig.selectedItem) + 1).toString())

            //Actualizamos el tipo de moneda en la App
            configPresenter.updateCurrentCurrency(binding.sCurrencyConfig.selectedItem as String)

            //Cargamos las traducciones generales y las almacenamos en una variable global
            this.mutableTranslations = configPresenter.getTranslations(languageSelected)

            //Establecemos las nuevas traducciones generales
            setTranslations()

            //Cargamos y establecemos las traducciones del menu de navegación
            setTranslationsMenu(configPresenter.getTranslationsMenu(languageSelected))

            //Mostramos un mensaje al usuario indicando que los cambios se han efectuado
            //correctamente
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_LANG_AND_CURR_UPDATED)!!.text
            )
        }

        //Inicializamos el click de Lugares de Almacenaje navegando a dicha pantalla
        binding.btnConfigStorePlaces.setOnClickListener { loadFragment(StorePlaceListFragment()) }

        //Inicializamos el click de Unidades de cantidad navegando a dicha pantalla
        binding.btnConfigQuantityUnit.setOnClickListener { loadFragment(QuantityUnitListFragment()) }
    }

    //Método llamado tras obtener los tipos de moneda de la base de datos SQLite
    private fun setCurrencies(currencies: List<String>) {

        //Almacenamos los tipos de moneda en una variable global
        this.currencies = currencies

        //Creamos un adapter y poblamos el combo con los tipos de moneda
        spinnerAdapterCurrency =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        spinnerAdapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Añadimos el adapter al spinner
        binding.sCurrencyConfig.adapter = spinnerAdapterCurrency

        //Obtenemos el tipo de moneda actual de la App
        val currentCurrency = configPresenter.getCurrentCurrency()

        //Seleccionamos el tipo de moneda actual en el combo
        binding.sCurrencyConfig.setSelection(currencies.indexOf(currentCurrency))

        //Inicializamos el evento que se produce al seleccionar una opción en el combo
        initOnItemSelectedListenerCurrency()
    }

    //Establecemos las traducciones del menu de navegación
    private fun setTranslationsMenu(translationsMenu: MutableMap<String, Translation>?) {
        activityMainBinding.bottomNavigation.menu.findItem(R.id.purchaseItem).title =
            translationsMenu?.get(Constant.MENU_PANTRY)!!.text
        activityMainBinding.bottomNavigation.menu.findItem(R.id.shopListItem).title =
            translationsMenu[Constant.MENU_SHOPPING]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(R.id.expirationItem).title =
            translationsMenu[Constant.MENU_EXPIRATION]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(R.id.recipeItem).title =
            translationsMenu[Constant.MENU_RECIPE]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(R.id.configItem).title =
            translationsMenu[Constant.MENU_CONFIG]!!.text
    }

    //Establecemos las traducciones generales
    override fun setTranslations() {
        binding.layoutConfig.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations?.get(Constant.TITLE_CONFIG)!!.text
        binding.lLanguageConfig.text = mutableTranslations?.get(Constant.LABEL_LANGUAGE)!!.text
        binding.lCurrencyConfig.text = mutableTranslations?.get(Constant.LABEL_CURRENCY)!!.text
        binding.etConfigEmail.hint = mutableTranslations?.get(Constant.FIELD_EMAIL)!!.text
        binding.etConfigPassword.hint = mutableTranslations?.get(Constant.FIELD_PASSWORD)!!.text
        binding.btnChangeLangAndCurrency.text =
            mutableTranslations?.get(Constant.BTN_LANG_AND_CURR)!!.text
        binding.btnChangeEmail.text = mutableTranslations?.get(Constant.BTN_CHANGE_EMAIL)!!.text
        binding.btnChangePassword.text =
            mutableTranslations?.get(Constant.BTN_CHANGE_PASSWORD)!!.text
        binding.btnConfigStorePlaces.text =
            mutableTranslations?.get(Constant.BTN_STORE_PLACES)!!.text
        binding.btnConfigQuantityUnit.text =
            mutableTranslations?.get(Constant.BTN_QUANTITY_UNIT)!!.text
    }

    private fun initOnItemSelectedListenerLanguage() {

        //Se ejecuta cada vez que se selecciona un elemento en el combo lenguaje
        binding.sLanguageConfig.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Asignamos el lenguaje segun la posición seleccionada (1 más porque
                    //el id de los lenguajes no empiezan en 0 si no en 1)
                    languageSelected = position + 1

                    //Obtenemos los tipos de moneda para un idioma concreto y lo cargamos en
                    //el combo
                    setCurrencies(configPresenter.getCurrencies(position + 1))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun initOnItemSelectedListenerCurrency() {

        //Se ejecuta cada vez que se selecciona un elemento del combo de tipo de moneda
        binding.sCurrencyConfig.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    //Se ejecuta cada vez que se recupera el email de usuario de la base de datos
    override fun onGottenEmail(result: OneValueEntity) {

        //Verifiacamos que la respuestra es correcta
        if (result.status == Constant.OK) {

            //Asignamos el valor al campo de texto
            binding.etConfigEmail.setText(result.value)
        }
    }

    //Se ejecuta cada vez que se recupera la contraseña de la base de datos
    override fun onGottenPassword(result: OneValueEntity) {

        //Verifiacamos que la respuestra es correcta
        if (result.status == Constant.OK) {

            //Asignamos el valor al campo de texto
            binding.etConfigPassword.setText(result.value)
        }
    }

    //Se ejecuta cada vez que se ha cambiado el correo de usuario en la base de datos
    override fun onChangeEmail(result: SimpleResponseEntity) {

        //Verificamos si el correo se ha cambiado o no con éxito y
        //mostramos un mensaje al usuario indicandoselo
        val msgResult = if (result.status == Constant.OK) {
            mutableTranslations?.get(Constant.MSG_EMAIL_UPDATED)!!.text
        } else {
            mutableTranslations?.get(Constant.MSG_EMAIL_NOT_UPDATED)!!.text
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }

    //Se ejecuta cada vez que se ha cambiado la contraseña de usuario en la base de datos
    override fun onChangePassword(result: SimpleResponseEntity) {

        //Verificamos si la contraseña se ha cambiado o no con éxito y
        //mostramos un mensaje al usuario indicandoselo
        val msgResult = if (result.status == Constant.OK) {
            mutableTranslations?.get(Constant.MSG_PASSWORD_UPDATED)!!.text
        } else {
            mutableTranslations?.get(Constant.MSG_PASSWORD_NOT_UPDATED)!!.text
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    private fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack (sirve para cuando
        //hacemos clic en el back button del movil)
        //Comiteamos
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}