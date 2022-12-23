package com.myfood.mvp.storeplacelist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.myfood.databases.databasesqlite.entity.StorePlace

class StorePlaceListPresenter(
    private val storePlaceListFragment: StorePlaceListFragment,
    context: Context
) : StorePlaceListContract.Presenter {

    //Declaración de variables globales
    private val storePlaceListModel: StorePlaceListModel = StorePlaceListModel()
    private var storePlaceAdapter: StorePlaceListAdapter
    private var storePlaceMutableList: MutableList<StorePlace> = mutableListOf()
    private var storePlaceFiltered: MutableList<StorePlace> = mutableListOf()
    private var currentLanguage: String

    init {

        //Creamos las instancias de las bases de datos
        storePlaceListModel.createInstances(context)

        //Obtenemos el idioma actual de la App
        currentLanguage =  storePlaceListModel.getCurrentLanguage()

        //Obtenemos la lista de lugares de almacenaje y la transformamos a una lista mutable
        storePlaceMutableList = storePlaceListModel.getStorePlaces().toMutableList()

        //Creamos el adapter para el recyclerview
        storePlaceAdapter = StorePlaceListAdapter(
            placeList = storePlaceMutableList,
            onClickDelete = { position, placeItem -> onDeleteItem(position, placeItem) },
            onClickUpdate = { placeItem -> onUpdateItem(placeItem) })

        //Inicializamos el recyclerview
        Handler(Looper.getMainLooper()).post {
            storePlaceListFragment.initRecyclerView(storePlaceAdapter)
        }
    }

    //Metodo que se ejecuta cuando varia el contenido del campo de texto del buscador
    override fun doFilter(userFilter: Editable?) {

        //Obtenemos la lista filtrada que coincide con la busqueda
        storePlaceFiltered = storePlaceMutableList.filter { place ->
            place.storePlace.lowercase().contains(userFilter.toString().lowercase())
        }.toMutableList()

        //Refrescamos el recyclerview con la lista filtrada
        storePlaceAdapter.updateStorePlaceList(storePlaceFiltered)
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = storePlaceListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que borra un lugar de almacenaje de la base de datos SQLite y la lista
    private fun onDeleteItem(position: Int, place: StorePlace) {

        //Borramos el elemento de la base de datos
        storePlaceListModel.deleteStorePlace(place.idStorePlace.toString())

        //Obtenemos la posicion del elemento en la lista total
        var count = 0
        var pos = 0
        storePlaceMutableList.forEach {
            if (it.idStorePlace == place.idStorePlace) {
                pos = count
            }
            count += 1
        }

        //Eliminamos el elemento de la lista total
        storePlaceMutableList.removeAt(pos)

        //Si la lista filtrada es no vacia (hemos realizado una busqueda) eliminamos
        //el item de dicha lista
        if (storePlaceFiltered.isNotEmpty()) storePlaceFiltered.removeAt(position)

        //Notificamos los cambios en el recyclerview
        storePlaceAdapter.notifyItemRemoved(position)
    }

    //Metodo que se ejecuta al pulsar el click de modificar en el item seleccionado
    private fun onUpdateItem(storePlaceList: StorePlace) {
        storePlaceListFragment.onUpdateStorePlace(storePlaceList)
    }

}
