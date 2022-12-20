package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.StorePlace
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.mvvm.data.model.OpenFoodEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity


class AddPantryModel : AddPantryContract.Model {

    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.PANTRY_PRODUCT.int)
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return myFoodRepository.getQuantitiesUnit()
    }

    override fun getPlaces(): List<StorePlace> {
        return myFoodRepository.getStorePlaces()
    }

    override fun getUserId(): String {
        return myFoodRepository.getUserId()
    }

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

    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return myFoodRepository.getPantryProduct(idPantry)
    }

    override fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
        return myFoodRepository.getOpenFoodProduct(url)
    }
}