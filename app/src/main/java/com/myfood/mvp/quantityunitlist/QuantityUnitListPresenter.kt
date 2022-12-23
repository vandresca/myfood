package com.myfood.mvp.quantityunitlist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.myfood.databases.databasesqlite.entity.QuantityUnit

class QuantityUnitListPresenter(
    private val quantityUnitListFragment: QuantityUnitListFragment,
    context: Context
) : QuantityUnitListContract.Presenter {

    //Declaramos las variables globales
    private lateinit var quantityUnitAdapter: QuantityUnitListAdapter
    private var quantityUnitMutableList: MutableList<QuantityUnit> = mutableListOf()
    private var quantityUnitFiltered: MutableList<QuantityUnit> = mutableListOf()
    private val quantityUnitListModel: QuantityUnitListModel = QuantityUnitListModel()
    private val currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        quantityUnitListModel.createInstances(context)

        //Obtenemos el idioma actual de la App
        currentLanguage = quantityUnitListModel.getCurrentLanguage()

        //Obtenemos la lista de unidades de cantidad y la transformamos a una lista mutable
        quantityUnitMutableList = quantityUnitListModel.getQuantityUnits().toMutableList()

        //Creamos el adapter para el listado de unidades de cantidad
        quantityUnitAdapter = QuantityUnitListAdapter(
            quantityUnitList = quantityUnitMutableList,
            onClickDelete = { position, QuantityUnitItem ->
                onDeleteItem(
                    position,
                    QuantityUnitItem
                )
            },
            onClickUpdate = { QuantityUnitItem -> onUpdateItem(QuantityUnitItem) })

        //Inicializamos el recyclerview
        Handler(Looper.getMainLooper()).post {
            quantityUnitListFragment.initRecyclerView(quantityUnitAdapter)
        }
    }

    //Metodo que se ejecuta cada vez que modificamos el contenido del campo de texto del buscador
    override fun doFilter(userFilter: Editable?) {

        //Obtenemos la lista de elemntos filtrados segun el contenido del campo de testo del buscador
        //Lo transformamos a una lista mutable
        quantityUnitFiltered = quantityUnitMutableList.filter { QuantityUnit ->
            QuantityUnit.quantityUnit.lowercase().contains(userFilter.toString().lowercase())
        }.toMutableList()

        //Actualizamos el recyclerview con los elmentos filtrados
        quantityUnitAdapter.updateQuantityUnitList(quantityUnitFiltered)
    }


    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = quantityUnitListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que se ejecuta cuando se pulsa el boton eliminar de un elemento de la lista
    private fun onDeleteItem(position: Int, quantityUnit: QuantityUnit) {

        //Borramos la unidad de cantidad de la base de datos MySQL
        quantityUnitListModel.deleteQuantityUnit(quantityUnit.idQuantityUnit.toString())

        //Inicializamos un contador y una posicion
        var count = 0
        var pos = 0

        //Obtenemos la posicion de la lista total en la que se encuentra el elemento
        quantityUnitMutableList.forEach {
            if (it.idQuantityUnit == quantityUnit.idQuantityUnit) {
                pos = count
            }
            count += 1
        }

        //Lo eliminamos de la lista total
        quantityUnitMutableList.removeAt(pos)

        //Si la lista de filtrado no esta vacia (entonces es que hemos buscado algo). Quitamos
        //el elemento de la posicion que hemos seleccionado
        if (quantityUnitFiltered.isNotEmpty()) quantityUnitFiltered.removeAt(position)

        //Refrescamos el recyclerview
        quantityUnitAdapter.notifyItemRemoved(position)
    }

    //Metodo que se ejcuta al clicar en el boton modificar del item de la lista
    private fun onUpdateItem(QuantityUnitList: QuantityUnit) {
        quantityUnitListFragment.onUpdateQuantityUnit(QuantityUnitList)
    }

}
