package com.myfood.mvp.shoplist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.ShopListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class ShopListModel : ShopListContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de las bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el idioma  actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla Compra
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.SHOPPING_LIST.int)
    }

    //Metodo que obtiene el id de usuario actual de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene la lista de producto compra del usuario actual
    override fun getShopList(idUser: String): MutableLiveData<ShopListEntity> {
        return myFoodRepository.getShopList(idUser)
    }

    //Metodo que elimina un producto de compra de la base de datos dado su id
    override fun deleteShop(idShop: String): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.deleteShop(idShop)
    }

}