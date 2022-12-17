package com.example.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.ShopListEntity


class ShopListModel : ShopListContract.Model {

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

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    override fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {
        return myFoodRepository.getShopList(idUser)
    }

    override fun deleteShop(idShop: String) {
        myFoodRepository.deleteShop(idShop)
    }

}