package com.example.myfood.mvp.addplace

import android.content.Context
import com.example.myfood.databasesqlite.entity.Translation

class AddStorePlacePresenter(
    private val addStorePlaceView: AddStorePlaceContract.View,
    private val addStorePlaceModel: AddStorePlaceContract.Model,
    context: Context
) : AddStorePlaceContract.Presenter {
    init {
        addStorePlaceModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return addStorePlaceModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = addStorePlaceModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun addStorePlace(storePlace: String) {
        addStorePlaceModel.addStorePLace(storePlace)
    }

    override fun updateStorePlace(storePlace: String, idStorePlace: String) {
        addStorePlaceModel.updateStorePlace(storePlace, idStorePlace)
    }
}