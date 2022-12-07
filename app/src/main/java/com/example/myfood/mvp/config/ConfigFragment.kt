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
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.databinding.ConfigFragmentBinding
import com.example.myfood.mvp.place.PlaceListFragment
import com.example.myfood.mvp.quantityunit.QuantityUnitListFragment
import com.example.myfood.popup.Popup

class ConfigFragment(private var activityMainBinding: ActivityMainBinding) : Fragment(),
    ConfigContract.View {
    private var _binding: ConfigFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var spinnerAdapterLanguage: ArrayAdapter<String>
    private lateinit var spinnerAdapterCurrency: ArrayAdapter<String>
    private lateinit var configModel: ConfigModel
    private lateinit var contextt: Context
    private lateinit var idUser: String
    private lateinit var languages: List<String>
    private lateinit var currencies: List<String>
    private val thisClass: ConfigFragment = this
    private var languageSelected: Int = 0
    private lateinit var mutableTranslations: MutableMap<String, Translation>

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
        binding.layoutConfig.visibility = View.INVISIBLE
        this.context?.let { contextt = it }
        configModel = ConfigModel()
        configModel.getInstance(contextt)
        configModel.getLanguages(this)
        configModel.getUserId(this)
        initClickButtons()
    }

    private fun initClickButtons() {
        binding.btnChangeEmail.setOnClickListener {
            val email = binding.etConfigEmail.text.toString()
            val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            if (email.isEmpty()) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations["emptyEmail"]!!.text
                )
            } else if (!EMAIL_REGEX.toRegex().matches(email)) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations["formatEmail"]!!.text
                )
            } else {
                configModel.changeEmail(this, email, idUser)
            }
        }
        binding.btnChangeLangAndCurrency.setOnClickListener {
            configModel.updateCurrentLanguage((languages.indexOf(binding.sLanguageConfig.selectedItem) + 1).toString())
            configModel.updateCurrentCurrency(binding.sCurrencyConfig.selectedItem as String)
            configModel.getTranslationsMenu(this, languageSelected)
            configModel.getTranslations(this, languageSelected)

            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations["langAndCurrUpdated"]!!.text
            )
        }
        binding.btnConfigStorePlaces.setOnClickListener { loadFragment(PlaceListFragment()) }
        binding.btnConfigQuantityUnit.setOnClickListener { loadFragment(QuantityUnitListFragment()) }
    }


    override fun onLanguagesLoaded(languages: List<String>) {
        this.languages = languages
        spinnerAdapterLanguage =
            ArrayAdapter(contextt, android.R.layout.simple_spinner_item, languages)
        spinnerAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageConfig.adapter = spinnerAdapterLanguage
        configModel.getCurrentLanguage(this)
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
        configModel.getCurrentCurrency(this)
        initOnItemSelectedListenerCurrency()
    }

    override fun onUserIdLoaded(idUser: String) {
        this.idUser = idUser
        configModel.getEmail(this, idUser)
    }

    override fun onCurrentCurrencyLoaded(currency: String) {
        binding.sCurrencyConfig.setSelection(currencies.indexOf(currency))
    }

    override fun onTranslationsMenuLoaded(translations: List<Translation>) {
        var mutableTranslationsMenu = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslationsMenu.put(it.word, it)
        }
        activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.purchaseItem).title =
            mutableTranslationsMenu["menuPurchase"]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.shopListItem).title =
            mutableTranslationsMenu["menuShopping"]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.expirationItem).title =
            mutableTranslationsMenu["menuExpiration"]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.recipeItem).title =
            mutableTranslationsMenu["menuRecipe"]!!.text
        activityMainBinding.bottomNavigation.menu.findItem(com.example.myfood.R.id.configItem).title =
            mutableTranslationsMenu["menuConfig"]!!.text
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslations.put(it.word, it)
        }
        binding.layoutConfig.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["configTitle"]!!.text
        binding.lLanguageConfig.text = mutableTranslations["language"]!!.text
        binding.lCurrencyConfig.text = mutableTranslations["currency"]!!.text
        binding.etConfigEmail.hint = mutableTranslations["Email"]!!.text
        binding.btnChangeLangAndCurrency.text = mutableTranslations["changeLangAndCurrency"]!!.text
        binding.btnChangeEmail.text = mutableTranslations["changeEmail"]!!.text
        binding.btnConfigChangePassword.text = mutableTranslations["changePassword"]!!.text
        binding.btnConfigStorePlaces.text = mutableTranslations["places"]!!.text
        binding.btnConfigQuantityUnit.text = mutableTranslations["quantitUnitsConfig"]!!.text
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
                        configModel.getTranslations(thisClass, position + 1)
                    }
                    languageSelected = position + 1
                    configModel.getCurrencies(thisClass, position + 1)
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
            msgResult = mutableTranslations["emailUpdated"]!!.text
        } else {
            msgResult = mutableTranslations["emailNotUpdated"]!!.text
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}