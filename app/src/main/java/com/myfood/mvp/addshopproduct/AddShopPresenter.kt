package com.myfood.mvp.addshopproduct

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import com.myfood.databases.databasesqlite.entity.QuantityUnit

class AddShopPresenter(
    private val addShopFragment: AddShopFragment,
    private val context: Context
) : AddShopContract.Presenter {

    //Declaramos las variables globales
    private var addShopModel: AddShopModel = AddShopModel()
    private var quantitiesUnit: List<QuantityUnit>
    private var userId:String

    init {

        //Creamos las instacias de la base de datos
        addShopModel.createInstances(context)

        //Obtenemos el id de usuario de la App y lo asignamos a una variable global
        userId = addShopModel.getUserId()

        //Obtenemos la lista de unidades de cantidad
        quantitiesUnit = addShopModel.getQuantitiesUnit()
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

    //Metodo general para crear un adapter (unidades de cantidad o lugares de
    //almacenaje)
    fun createAdapterQuantitiesUnit(): ArrayAdapter<String> {

        //Declaramos una variable de tipo lista mutable
        //de tipo String.
        //Recorremos los objetos segun el caso de unidad de cantidad o de
        //lugar de almacenaje y obtenemos solo el String
        val mutableList: MutableList<String> = mutableListOf()
        this.quantitiesUnit.forEach {
            mutableList.add(it.quantityUnit)
        }
        //Poblamos el combo con la lista
        val adapter = ArrayAdapter(
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
        val currentLanguage = addShopModel.getCurrentLanguage()
        val translations = addShopModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que devuelve los atributos de un producto de compra dado su id
    override fun getShopProduct(idShop: String) {
        addShopModel.getShopProduct(idShop).
        observe(addShopFragment)
        { data -> addShopFragment.onLoadShopToUpdate(data) }
    }

    //Metodo que inserta un producto de compra para un usuario en la base de datos
    override fun insertShop(
        name: String,
        quantity: String,
        quantityUnit: String,
    ){
        addShopModel.insertShop(name, quantity, quantityUnit, userId)
            .observe(addShopFragment) { result ->
            if (result.status == com.myfood.constants.Constant.OK) {
                addShopFragment.onInsertedOrUpdatedProduct()
            }
        }
    }

    //Metodo que actualiza un producto de compra en base de datos dado su id
    override fun updateShop(
        name: String,
        quantity: String,
        quantityUnit: String,
        idShop: String
    ){
        addShopModel.updateShop(name, quantity, quantityUnit, idShop).
        observe(addShopFragment) { result ->
            if (result.status == com.myfood.constants.Constant.OK) {
                addShopFragment.onInsertedOrUpdatedProduct()
            }
        }
    }
}