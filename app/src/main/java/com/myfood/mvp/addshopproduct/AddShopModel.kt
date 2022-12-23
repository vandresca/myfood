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

    //Declaramos una instancia del repositorio de metodos de acceso a la base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que instancia la base de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguaje actual de base de datos
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones para la pantalla de Compras
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.SHOPPING_LIST.int)
    }

    //Metodo que obtiene las unidad de cantidad de la App
    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    //Metodo que obtiene el id de usuario de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que obtiene los atributos del producto de compra dado su id
    override fun getShopProduct(idShop: String): MutableLiveData<ShopProductEntity> {
        return myFoodRepository.getShopProduct(idShop)
    }

    //Metodo que inserta un  producto de compra para un usuario en la base de datos
    override fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        userId: String
    ): MutableLiveData<OneValueEntity> {
        return myFoodRepository.insertShop(name, quantity, quantityUnit, userId)
    }

    //Metodo que actualiza un producto de compra dado su id en la base de datos
    override fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.updateShop(name, quantity, quantityUnit, idShop)
    }
}