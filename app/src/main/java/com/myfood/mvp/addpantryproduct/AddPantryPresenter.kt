package com.myfood.mvp.addpantryproduct

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.StorePlace

class AddPantryPresenter(
    private val addPantryFrament: AddPantryFragment,
    private val context: Context
) : AddPantryContract.Presenter {

    //Declaramos las variables globales
    private val addPantryModel: AddPantryModel = AddPantryModel()
    private val userId:String
    private val quantitiesUnit: List<QuantityUnit>
    private val storePlaces: List<StorePlace>

    //Metodo que se lanza al iniciar la clase presentador
    init {
        //Instanciamos las bases de datos
        addPantryModel.createInstances(context)

        //Obtenemos el usuario de la App
        userId = addPantryModel.getUserId()

        //Obtenemos las unidades de cantidad de la App
        quantitiesUnit = addPantryModel.getQuantitiesUnit()

        //Obtenemos los lugares de almacenaje de la App
        storePlaces = addPantryModel.getStorePlaces()
    }

    //Metodo que obtiene la posicion de una determinada unidad de cantidad en
    //la lista de unidades de cantidad
    fun getPositionQuantitiesUnit(quantityUnit: String): Int{
        val list: MutableList<String> = mutableListOf()
        quantitiesUnit.forEach{
            list += it.quantityUnit
        }
        return list.indexOf(quantityUnit)
    }

    //Metodo que obtiene la posición  de un determinado lugar de almacenaje en
    //la lista de lugares de almacenaje
    fun getPositionStorePlaces(storePlace: String): Int{
        val list: MutableList<String> = mutableListOf()
        storePlaces.forEach{
            list += it.storePlace
        }
        return list.indexOf(storePlace)
    }

    //Metodo que crea un adapter para el spinner de unidades de cantidad
    fun createAdapterQuantityUnit(): ArrayAdapter<String>{
        return createAdapter(this.quantitiesUnit)
    }

    //Metodo que crea un adapter para el spinner de lugares de almacenaje
    fun createAdapterStorePlace(): ArrayAdapter<String>{
        return createAdapter(this.storePlaces)
    }

    //Metodo general para crear un adapter (unidades de cantidad o lugares de
    //almacenaje)
    private fun <T> createAdapter(list: List<T>): ArrayAdapter<String>{

        //Declaramos una variable de tipo lista mutable
        //de tipo String.
        //Recorremos los objetos segun el caso de unidad de cantidad o de
        //lugar de almacenaje y obtenemos solo el String
        val mutableList: MutableList<String> = mutableListOf()
        list.forEach {
            if(it is QuantityUnit) mutableList.add(it.quantityUnit)
            if(it is StorePlace) mutableList.add(it.storePlace)
        }
        //Poblamos el combo con la lista
        val adapter =ArrayAdapter(
            context,
            R.layout.simple_spinner_item,
            mutableList
        )
        // Especifica el layout para usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    //Metodo que retorna las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val currentLanguage = addPantryModel.getCurrentLanguage()
        val translations = addPantryModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    fun fillProductOpenFood(barcode: String){
        addPantryModel.getOpenFoodProduct(
            "${com.myfood.constants.Constant.OPEN_FOOD_URL}$barcode.json")
            .observe(addPantryFrament)
            { result -> addPantryFrament.onFillProductOpenFood(result) }
    }

    //Metodo que obtiene el producto de despensa dado su id
    override fun getPantryProduct(idPantry: String) {
        addPantryModel.getPantryProduct(idPantry).
        observe(addPantryFrament)
        { data->addPantryFrament.onLoadPantryToUpdate(data) }
    }

    //Metodo que inserta un producto despensa en la base de datos para un usuario
    override fun insertPantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String) {
        addPantryModel.insertPantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, userId
        ).observe(addPantryFrament) { result ->
            if (result.status == com.myfood.constants.Constant.OK) {
                addPantryFrament.onInsertedOrUpdatedPantry()
            }
        }
    }

    //Metodo que inserta un producto despensa en la base de datos dado su id
    override fun updatePantry(
        barcode: String, name: String, quantity: String,
        quantityUnit: String, place: String, weight: String, price: String,
        expirationDate: String, preferenceDate: String, image: String,
        brand: String, idPantry: String) {
        addPantryModel.updatePantry(
            barcode, name, quantity, quantityUnit, place,
            weight, price, expirationDate, preferenceDate, image, brand, idPantry
        ).observe(addPantryFrament) { result ->
            if (result.status == com.myfood.constants.Constant.OK) {
                addPantryFrament.onInsertedOrUpdatedPantry()
            }
        }
    }
}