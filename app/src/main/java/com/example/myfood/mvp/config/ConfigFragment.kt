package com.example.myfood.mvp.config

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.databinding.ConfigFragmentBinding
import com.example.myfood.popup.Popup

class ConfigFragment(private var activityMainBinding: ActivityMainBinding) : Fragment(),
    ConfigContract.View {
    private var _binding: ConfigFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var spinnerAdapterLanguage: ArrayAdapter<String>
    private lateinit var spinnerAdapterCurrency: ArrayAdapter<String>
    private lateinit var configModel: ConfigContract.Model
    private lateinit var cModel: ConfigModel
    private lateinit var contextt: Context
    private lateinit var idUser: String
    private lateinit var languages: List<String>
    private lateinit var currencies: List<String>
    private val thisClass: ConfigFragment = this
    private var languageSelected: Int = 0

    companion object {
        private const val CONST_OK = "OK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ConfigFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.context?.let { contextt = it }
        cModel = ConfigModel()
        configModel = cModel
        cModel.getInstance(contextt)
        cModel.getLanguages(this)
        cModel.getUserId(this)
        binding.btnChangeEmail.setOnClickListener {
            val email = binding.etConfigEmail.text.toString()
            val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            if (email.isEmpty()) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    "El correo electrónico no puede estar vacío"
                )
            } else if (!EMAIL_REGEX.toRegex().matches(email)) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    "Formato de correo electrónico incorrecto"
                )
            } else {
                configModel.changeEmail(this, email, idUser)
            }
        }
        binding.btnChangeLangAndCurrency.setOnClickListener {
            cModel.updateCurrentLanguage((languages.indexOf(binding.sLanguageConfig.selectedItem) + 1).toString())
            cModel.updateCurrentCurrency(binding.sCurrencyConfig.selectedItem as String)
            cModel.getTranslationsMenu(this, languageSelected)
            cModel.getTranslations(this, languageSelected)
            Popup.showInfo(requireContext(), resources, "Se ha actualizado el idioma y moneda")
        }
    }

    override fun onLanguagesLoaded(languages: List<String>) {
        this.languages = languages
        spinnerAdapterLanguage =
            ArrayAdapter(contextt, android.R.layout.simple_spinner_item, languages)
        spinnerAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageConfig.adapter = spinnerAdapterLanguage
        cModel.getCurrentLanguage(this)
        initOnItemSelectedListenerLanguage()
    }

    override fun onCurrentLanguageLoaded(language: String) {
        binding.sLanguageConfig.setSelection(language.toInt() - 1)
    }

    override fun onCurrenciesLoaded(currencies: List<String>) {
        this.currencies = currencies
        spinnerAdapterCurrency =
            ArrayAdapter(contextt, android.R.layout.simple_spinner_item, currencies)
        spinnerAdapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sCurrencyConfig.adapter = spinnerAdapterCurrency
        cModel.getCurrentCurrency(this)
        initOnItemSelectedListenerCurrency()
    }

    override fun onUserIdLoaded(idUser: String) {
        this.idUser = idUser
        cModel.getEmail(this, idUser)
    }

    override fun onCurrentCurrencyLoaded(currency: String) {
        binding.sCurrencyConfig.setSelection(currencies.indexOf(currency))
    }

    override fun onTranslationsMenuLoaded(translations: List<Translation>) {
        translations.forEach {
            when (it.word) {
                "menuPurchase" -> activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.purchaseItem).title =
                    it.text
                "menuShopping" -> activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.shopListItem).title =
                    it.text
                "menuExpiration" -> activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.expirationItem).title =
                    it.text
                "menuRecipe" -> activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.recipeItem).title =
                    it.text
                "menuConfig" -> activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.configItem).title =
                    it.text

            }
        }
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        translations.forEach {
            when (it.word) {
                "configTitle" -> binding.header.titleHeader.text = it.text
                "language" -> binding.lLanguageConfig.text = it.text
                "currency" -> binding.lCurrencyConfig.text = it.text
                "Email" -> binding.etConfigEmail.hint = it.text
                "changePassword" -> binding.btnConfigChangePassword.text = it.text
                "places" -> binding.btnConfigStorePlaces.text = it.text
            }
        }
    }

    private fun initOnItemSelectedListenerLanguage() {
        binding.sLanguageConfig.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (languageSelected == 0) {
                        cModel.getTranslations(thisClass, position + 1)
                    }
                    languageSelected = position + 1
                    cModel.getCurrencies(thisClass, position + 1)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun initOnItemSelectedListenerCurrency() {
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

    override fun onGottenEmail(response: String?) {
        Handler(Looper.getMainLooper()).post {
            binding.etConfigEmail.setText(response)
        }
    }

    override fun onChangeEmail(response: String?) {
        var msgResult = ""
        if (response.equals(CONST_OK)) {
            msgResult = "Se ha modificado el correo electrónico con éxito"
        } else {
            msgResult = "No se ha podido modificar el correo electrónico"
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }
}