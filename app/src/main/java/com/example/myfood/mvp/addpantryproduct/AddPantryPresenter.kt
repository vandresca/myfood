package com.example.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.OpenFoodEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity

class AddPantryPresenter(
    private val addPantryView: AddPantryContract.View,
    private val addPantryModel: AddPantryContract.Model,
    context: Context
) : AddPantryContract.Presenter {
    init {
        addPantryModel.getInstance(context)
    }

    override fun getCurrentLanguage(): String {
        return addPantryModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = addPantryModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return addPantryModel.getQuantitiesUnit()
    }

    override fun getUserId(): String {
        return addPantryModel.getUserId()
    }

    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return addPantryModel.getPantryProduct(idPantry)
    }

    override fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
        return addPantryModel.getOpenFoodProduct(url)
    }

    override fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String, userId: String
    ) {
        addPantryModel.insertPantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, userId
        )
    }

    override fun updatePantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String, idPantry: String
    ) {
        addPantryModel.updatePantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, idPantry
        )
    }
}