package com.myfood.mvp.addpantryproduct

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.databases.databasemysql.entity.OpenFoodEntity
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.Translation

class AddPantryPresenter(
    private val addPantryView: AddPantryContract.View,
    private val addPantryModel: AddPantryContract.Model,
    context: Context
) : AddPantryContract.Presenter {

    //Metodo que se lanza al iniciar la clase presentador
    init {
        //Instanciamos las bases de datos
        addPantryModel.getInstance(context)
    }

    //Metodo que obtiene el lenguaje actual de la App
    override fun getCurrentLanguage(): String {
        return addPantryModel.getCurrentLanguage()
    }

    //Metodo que obtiene las traducciones de la pantalla en el lenguaje indicado
    override fun getTranslations(language: Int): MutableMap<String, Translation> {

        //Declaramos un Map mutable de clave valor: String, Translation
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()

        //Realizamos la llamada al metodo getTranslation
        val translations = addPantryModel.getTranslations(language)

        //Transformamos los datos de la lista de objetos translations recibido
        //en un map de clave valor String-Translation
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    //Metodo que obtiene las unidades de cantidad de la App
    override fun getQuantitiesUnit(): List<QuantityUnit> {
        return addPantryModel.getQuantitiesUnit()
    }

    //Metodo que obtiene el id de usuario de la App
    override fun getUserId(): String {
        return addPantryModel.getUserId()
    }

    //Metodo que obtiene el producto de despensa dado su id
    override fun getPantryProduct(idPantry: String): MutableLiveData<PantryProductEntity> {
        return addPantryModel.getPantryProduct(idPantry)
    }

    //Metodo que realiza una llamada a la API de Openfood con la url de un código de barras
    //y retorna los atributos del producto alimenticio
    override fun getOpenFoodProduct(url: String): MutableLiveData<OpenFoodEntity> {
        return addPantryModel.getOpenFoodProduct(url)
    }

    //Metodo que inserta un producto despensa en la base de datos para un usuario
    override fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String, userId: String
    ): MutableLiveData<OneValueEntity> {
        return addPantryModel.insertPantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, userId
        )
    }

    //Metodo que inserta un producto despensa en la base de datos dado su id
    override fun updatePantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String, idPantry: String
    ): MutableLiveData<SimpleResponseEntity> {
        return addPantryModel.updatePantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, idPantry
        )
    }
}