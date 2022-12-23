package com.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.OpenFoodEntity
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.enum.ScreenType


class AddPantryModel : AddPantryContract.Model {

    //Declaramos una variable para obtener el repositorio de metodos de base de datos
    private val myFoodRepository = com.myfood.databases.MyFoodRepository()

    //Metodo que crea las instancias de la bases de datos
    override fun createInstances(context: Context) {
        myFoodRepository.createInstances(context)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones para un lenguaje concreto de la pantalla
    //producto despensa
    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    //Metodo que obtiene las unidades de cantidad de la App
    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    //Metodo que obtiene los lugares de almacenaje de la App
    override fun getStorePlaces(): List<StorePlace> {
        return myFoodRepository.getStorePlaces()
    }

    //Metodo que obtiene el id de usuario de la App
    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

    //Metodo que inserta un producto de despensa en base de datos para un usuario determinado
    override fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String, brand: String, userId: String
    ): MutableLiveData<OneValueEntity> {
        return myFoodRepository.insertPantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, userId
        )
    }

    //Metodo que actualiza un producto de despensa en base de datos dado su id
    override fun updatePantry(
        barcode: String,
        name: String,
        quantity: String,
        quantityUnit: String,
        place: String,
        weight: String,
        price: String,
        expirationDate: String,
        preferenceDate: String,
        image: String,
        brand: String,
        idPantry: String
    ): MutableLiveData<SimpleResponseEntity> {
        return myFoodRepository.updatePantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, idPantry
        )
    }

    //Metodo que obtiene los atributos del producto de despensa dado su id
    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return myFoodRepository.getPantryProduct(idPantry)
    }

    //Metodo que realiza la llamada a la API de Openfood con un codigo de barras mediante url
    //y retorna los atributos del producto alimenticio.
    override fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
        return myFoodRepository.getOpenFoodProduct(url)
    }
}