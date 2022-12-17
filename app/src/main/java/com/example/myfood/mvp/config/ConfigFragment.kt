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
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.databinding.ConfigFragmentBinding
import com.example.myfood.mvp.quantityunit.QuantityUnitListFragment
import com.example.myfood.mvp.storeplace.StorePlaceListFragment
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
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
    private var languageSelected: Int = 0
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var configPresenter: ConfigPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfigFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutConfig.visibility = View.INVISIBLE
        this.context?.let { contextt = it }
        configModel = ConfigModel()
        configPresenter = ConfigPresenter(this, configModel, requireContext())
        initLanguages()
        initTranslations()
        initUser()
        initOnItemSelectedListenerLanguage()
        initClickButtons()
    }

    private fun initLanguages() {
        val languages = configPresenter.getLanguages()
        this.languages = languages
        spinnerAdapterLanguage =
            ArrayAdapter(contextt, android.R.layout.simple_spinner_item, languages)
        spinnerAdapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageConfig.adapter = spinnerAdapterLanguage
        val currentLanguage = configPresenter.getCurrentLanguage()
        binding.sLanguageConfig.setSelection(currentLanguage.toInt() - 1)
    }

    private fun initTranslations() {
        val currentLanguage = configPresenter.getCurrentLanguage()
        this.mutableTranslations = configPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
    }

    private fun initUser() {
        val idUser = configPresenter.getUserId()
        this.idUser = idUser
        configPresenter.getEmail(idUser)
            .observe(this.viewLifecycleOwner) { response -> onGottenEmail(response) }
        configPresenter.getPassword(idUser)
            .observe(this.viewLifecycleOwner) { response -> onGottenPassword(response) }
    }

    private fun initClickButtons() {
        binding.btnChangeEmail.setOnClickListener {
            val email = binding.etConfigEmail.text.toString()
            val emailREGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
            if (email.isEmpty()) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_EMAIL_REQUIRED_CONF)!!.text
                )
            } else if (!emailREGEX.toRegex().matches(email)) {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_EMAIL_FORMAT_INCORRECT_CONF)!!.text
                )
            } else {
                configPresenter.changeEmail(email, idUser).observe(this.viewLifecycleOwner)
                { response -> onChangeEmail(response) }
            }
        }
        binding.btnChangePassword.setOnClickListener {
            val password = binding.etConfigPassword.text.toString()
            configPresenter.changePassword(password, idUser).observe(this.viewLifecycleOwner)
            { response -> onChangePassword(response) }
        }
        binding.btnChangeLangAndCurrency.setOnClickListener {
            configPresenter.updateCurrentLanguage((languages.indexOf(binding.sLanguageConfig.selectedItem) + 1).toString())
            configPresenter.updateCurrentCurrency(binding.sCurrencyConfig.selectedItem as String)
            this.mutableTranslations = configPresenter.getTranslations(languageSelected)
            setTranslations()
            setTranslationsMenu(configPresenter.getTranslationsMenu(languageSelected))
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_LANG_AND_CURR_UPDATED)!!.text
            )
        }
        binding.btnConfigStorePlaces.setOnClickListener { loadFragment(StorePlaceListFragment()) }
        binding.btnConfigQuantityUnit.setOnClickListener { loadFragment(QuantityUnitListFragment()) }
    }


    private fun setCurrencies(currencies: List<String>) {
        this.currencies = currencies
        spinnerAdapterCurrency =
            ArrayAdapter(contextt, android.R.layout.simple_spinner_item, currencies)
        spinnerAdapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sCurrencyConfig.adapter = spinnerAdapterCurrency
        val currentCurrency = configPresenter.getCurrentCurrency()
        binding.sCurrencyConfig.setSelection(currencies.indexOf(currentCurrency))
        initOnItemSelectedListenerCurrency()
    }

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
        binding.sLanguageConfig.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    languageSelected = position + 1
                    setCurrencies(configPresenter.getCurrencies(position + 1))
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

    override fun onGottenEmail(result: OneValueEntity) {
        if (result.status == Constant.OK) {
            Handler(Looper.getMainLooper()).post {
                binding.etConfigEmail.setText(result.value)
            }
        }
    }

    override fun onGottenPassword(result: OneValueEntity) {
        if (result.status == Constant.OK) {
            Handler(Looper.getMainLooper()).post {
                binding.etConfigPassword.setText(result.value)
            }
        }
    }

    override fun onChangeEmail(result: SimpleResponseEntity) {
        val msgResult = if (result.status == Constant.OK) {
            mutableTranslations?.get(Constant.MSG_EMAIL_UPDATED)!!.text
        } else {
            mutableTranslations?.get(Constant.MSG_EMAIL_NOT_UPDATED)!!.text
        }
        Popup.showInfo(requireContext(), resources, msgResult)
    }

    override fun onChangePassword(result: SimpleResponseEntity) {
        val msgResult = if (result.status == Constant.OK) {
            mutableTranslations?.get(Constant.MSG_PASSWORD_UPDATED)!!.text
        } else {
            mutableTranslations?.get(Constant.MSG_PASSWORD_NOT_UPDATED)!!.text
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