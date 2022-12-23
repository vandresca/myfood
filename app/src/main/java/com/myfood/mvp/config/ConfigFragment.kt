package com.myfood.mvp.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databinding.ActivityMainBinding
import com.myfood.databinding.ConfigFragmentBinding
import com.myfood.mvp.quantityunitlist.QuantityUnitListFragment
import com.myfood.mvp.storeplacelist.StorePlaceListFragment
import com.myfood.popup.Popup

class ConfigFragment(private var activityMainBinding: ActivityMainBinding) : Fragment(),
    ConfigContract.View {

    //Declaración variables globales
    private var _binding: ConfigFragmentBinding? = null
    private val binding get() = _binding!!
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()
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

        //Creamos el presentador
        configPresenter = ConfigPresenter(this, requireContext())

        //Inicializamos el combo lenguajes
        initLanguages()

        //Obtenemos las traducciones de pantalla
        mutableTranslations = configPresenter.getTranslationsScreen()
        setTranslations()

        //Obtenemos el email y la contraseña actual
        loadEmailAndPassword()

        //Inicializamos los clicks de los botones
        initClickButtons()
    }

    private fun initLanguages() {

        //Añadimos el adapter al spinner
        binding.sLanguageConfig.adapter = configPresenter.createAdapterLanguages()

        //Seleccionamos en el combo el lenguaje actual
        binding.sLanguageConfig.setSelection(configPresenter.getCurrentLanguage() - 1)

        //Inicializamos el evento que se produce al seleccionar una opción del combo
        binding.sLanguageConfig.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    //Obtenemos los tipos de moneda para un idioma concreto y lo cargamos en
                    //el combo
                    val languageSelected = position + 1
                    configPresenter.setCurrentLanguage(languageSelected.toString())
                    setCurrencies()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun loadEmailAndPassword() {

        //Obtenemos el email del usuario
        configPresenter.getEmail()

        //Obtenemos el password del usuario
        configPresenter.getPassword()
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
                    mutableTranslations[Constant.MSG_EMAIL_REQUIRED_CONF]!!
                )
                //Si el formato de correo es incorrecto mostramos un menaje al usuario indicandolo
            } else if (!emailREGEX.toRegex().matches(email)) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations[Constant.MSG_EMAIL_FORMAT_INCORRECT_CONF]!!
                )
                //Si todo es correcto modificamos el correo antiguo por el nuevo para ese usuario
            } else {
                configPresenter.changeEmail(email)
            }
        }

        //Inicializamos el click para el boton  cambiar contraseña
        binding.btnChangePassword.setOnClickListener {
            //Recuperamos el valor del campo de texto
            val password = binding.etConfigPassword.text.toString()

            //Cambiamos la contraseña
            configPresenter.changePassword(password)
        }

        //Inicializamos el click para el boton cambiar lenguage y tipo de moneda
        binding.btnChangeLangAndCurrency.setOnClickListener {

            //Actualizamos el lenguaje y el tipo de moneda en la App
            val newLanguage = configPresenter.getPositionLanguages(
                binding.sLanguageConfig.selectedItem.toString()) + 1
            val newCurrency = binding.sCurrencyConfig.selectedItem as String
            configPresenter.setCurrentLanguage(newLanguage.toString())
            configPresenter.setCurrentCurrency(newCurrency)

            //Cargamos las traducciones de pantalla y de menu de navegación
            this.mutableTranslations = configPresenter.getTranslationsScreen()
            setTranslations()
            setTranslationsMenu()

            //Mostramos un mensaje al usuario indicando que los cambios se han efectuado
            //correctamente
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations[Constant.MSG_LANG_AND_CURR_UPDATED]!!
            )
        }

        //Inicializamos el click de Lugares de Almacenaje navegando a dicha pantalla
        binding.btnConfigStorePlaces.setOnClickListener { loadFragment(StorePlaceListFragment()) }

        //Inicializamos el click de Unidades de cantidad navegando a dicha pantalla
        binding.btnConfigQuantityUnit.setOnClickListener { loadFragment(QuantityUnitListFragment()) }
    }

    //Método llamado tras obtener los tipos de moneda de la base de datos SQLite
    private fun setCurrencies() {

        //Añadimos el adapter al spinner
        binding.sCurrencyConfig.adapter = configPresenter.createAdapterCurrencies()

        //Seleccionamos el tipo de moneda actual en el combo
        binding.sCurrencyConfig.setSelection(
            configPresenter.getPositionCurrencies(configPresenter.getCurrentCurrency()))
    }

    //Establecemos las traducciones del menu de navegación
    private fun setTranslationsMenu() {
        val translationsMenu = configPresenter.getTranslationsMenu()
        activityMainBinding.bottomNavigation.menu.findItem(R.id.purchaseItem).title =
            translationsMenu[Constant.MENU_PANTRY]!!
        activityMainBinding.bottomNavigation.menu.findItem(R.id.shopListItem).title =
            translationsMenu[Constant.MENU_SHOPPING]!!
        activityMainBinding.bottomNavigation.menu.findItem(R.id.expirationItem).title =
            translationsMenu[Constant.MENU_EXPIRATION]!!
        activityMainBinding.bottomNavigation.menu.findItem(R.id.recipeItem).title =
            translationsMenu[Constant.MENU_RECIPE]!!
        activityMainBinding.bottomNavigation.menu.findItem(R.id.configItem).title =
            translationsMenu[Constant.MENU_CONFIG]!!
    }

    //Establecemos las traducciones generales
    override fun setTranslations() {
        binding.layoutConfig.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations[Constant.TITLE_CONFIG]!!
        binding.lLanguageConfig.text = mutableTranslations[Constant.LABEL_LANGUAGE]!!
        binding.lCurrencyConfig.text = mutableTranslations[Constant.LABEL_CURRENCY]!!
        binding.etConfigEmail.hint = mutableTranslations[Constant.FIELD_EMAIL]!!
        binding.etConfigPassword.hint = mutableTranslations[Constant.FIELD_PASSWORD]!!
        binding.btnChangeLangAndCurrency.text = mutableTranslations[Constant.BTN_LANG_AND_CURR]!!
        binding.btnChangeEmail.text = mutableTranslations[Constant.BTN_CHANGE_EMAIL]!!
        binding.btnChangePassword.text = mutableTranslations[Constant.BTN_CHANGE_PASSWORD]!!
        binding.btnConfigStorePlaces.text = mutableTranslations[Constant.BTN_STORE_PLACES]!!
        binding.btnConfigQuantityUnit.text = mutableTranslations[Constant.BTN_QUANTITY_UNIT]!!
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
            mutableTranslations[Constant.MSG_EMAIL_UPDATED]!!
        } else {
            mutableTranslations[Constant.MSG_EMAIL_NOT_UPDATED]!!
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }

    //Se ejecuta cada vez que se ha cambiado la contraseña de usuario en la base de datos
    override fun onChangePassword(result: SimpleResponseEntity) {

        //Verificamos si la contraseña se ha cambiado o no con éxito y
        //mostramos un mensaje al usuario indicandoselo
        val msgResult = if (result.status == Constant.OK) {
            mutableTranslations[Constant.MSG_PASSWORD_UPDATED]!!
        } else {
            mutableTranslations[Constant.MSG_PASSWORD_NOT_UPDATED]!!
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