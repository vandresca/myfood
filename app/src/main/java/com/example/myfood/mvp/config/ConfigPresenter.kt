package com.example.myfood.mvp.config

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class ConfigPresenter(
    private val configView: ConfigContract.View,
    private val configModel: ConfigContract.Model,
    context: Context
) : ConfigContract.Presenter {

    init {
        configModel.getInstance(context)
    }

    override fun getUserId(): String {
        return configModel.getUserId()
    }

    override fun getCurrentLanguage(): String {
        return configModel.getCurrentLanguage()
    }

    override fun changeEmail(email: String, user: String): MutableLiveData<SimpleResponseEntity> {
        return configModel.changeEmail(email, user)
    }

    override fun changePassword(
        password: String,
        user: String
    ): MutableLiveData<SimpleResponseEntity> {
        return configModel.changePassword(password, user)
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = configModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getTranslationsMenu(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = configModel.getTranslationsMenu(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getLanguages(): List<String> {
        return configModel.getLanguages()
    }

    override fun getCurrentCurrency(): String {
        return configModel.getCurrentCurrency()
    }

    override fun getEmail(user: String): MutableLiveData<OneValueEntity> {
        return configModel.getEmail(user)
    }

    override fun getPassword(user: String): MutableLiveData<OneValueEntity> {
        return configModel.getPassword(user)
    }

    override fun getCurrencies(language: Int): List<String> {
        return configModel.getCurrencies(language)
    }

    override fun updateCurrentLanguage(language: String) {
        configModel.updateCurrentLanguage(language)
    }

    override fun updateCurrentCurrency(currency: String) {
        configModel.updateCurrentCurrency(currency)
    }

}