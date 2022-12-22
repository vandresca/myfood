package com.myfood.mvp.addshopproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.ShopProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType

class AddShopModel : AddShopContract.Model {

    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

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