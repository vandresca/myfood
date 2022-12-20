package com.example.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.ShopProductEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

class AddShopModel : AddShopContract.Model {

    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.SHOPPING_LIST.int)
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {
        return myFoodRepository.getShopProduct(idShop)
    }

    override fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ): MutableLiveData<OneValueEntity> {
        return myFoodRepository.insertShop(name, quantity, quantityUnit, userId)
    }

    override fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.updateShop(name, quantity, quantityUnit, idShop)
    }
}