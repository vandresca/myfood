package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.ShopProductEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class AddShopPresenter(
    private val addShopView: AddShopContract.View,
    private val addShopModel: AddShopContract.Model,
    context: Context
) : AddShopContract.Presenter {
    init {
        addShopModel.getInstance(context)
    }

    override fun getUserId(): String {
        return addShopModel.getUserId()
    }


    override fun getCurrentLanguage(): String {
        return addShopModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = addShopModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return addShopModel.getQuantitiesUnit()
    }

    override fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {
        return addShopModel.getShopProduct(idShop)
    }

    override fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ): MutableLiveData<OneValueEntity> {
        return addShopModel.insertShop(name, quantity, quantityUnit, userId)
    }

    override fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ): MutableLiveData<SimpleResponseEntity> {
        return addShopModel.updateShop(name, quantity, quantityUnit, idShop)
    }
}