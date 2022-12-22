package com.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ShopListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class ShopListModel : ShopListContract.Model {

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

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {
        return myFoodRepository.getShopList(idUser)
    }

    override fun deleteShop(idShop: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.deleteShop(idShop)
    }

}