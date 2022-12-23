package com.myfood.mvp.pantrylist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.myfood.databases.databasemysql.entity.PantryListEntity

class PantryListPresenter(
    private val pantryListFragment: PantryListFragment,
    context: Context
) : PantryListContract.Presenter {

    //Declaramos las variables globales
    private lateinit var pantryAdapter: PantryListAdapter
    private var pantryMutableList: MutableList<PantryList> = mutableListOf()
    private var pantryFiltered: MutableList<PantryList> = mutableListOf()
    private var idUser: String
    private var currentLanguage:String
    private val pantryListModel: PantryListModel = PantryListModel()

    init {

        //Creamos las instancias de las bases de datos
        pantryListModel.createInstances(context)

        //Obtenemos el id de usuario actual de la App
        idUser = pantryListModel.getUserId()

        //Obtenemos el idioma actual de la App
        currentLanguage = pantryListModel.getCurrentLanguage()

        //Obtenemos el listado de productos despensa y lo pintamos en el recyclerview
        pantryListModel.getPantryList(idUser).
        observe(pantryListFragment) { data -> loadData(data) }
    }

    //Metodo que se ejecuta cuando se tiene la lista de productos despensa
    override fun loadData(pantryListEntity: PantryListEntity) {

        //Verificamos que la respuesta es correcta
        if (pantryListEntity.status == com.myfood.constants.Constant.OK) {

            //Convertimos el objeto entidad con la lista de productos a una lista de productos
            //de despensa
            pantryMutableList = pantryListEntity.toMVP().toMutableList()

            //Creamos el adapter para la lista de elementos
            pantryAdapter = PantryListAdapter(
                purchaseList = pantryMutableList,
                onClickListener = { purchaseItem -> onItemSelected(purchaseItem) },
                onClickDelete = { position, purchaseItem -> onDeleteItem(position, purchaseItem) },
                onClickUpdate = { purchaseItem -> onUpdateItem(purchaseItem) },
                currency = "  ${getCurrentCurrency()}"
            )

            //Iniciamos el recyclerview
            //Handler(Looper.getMainLooper()).post {
            pantryListFragment.initRecyclerView(pantryAdapter)
            //}
        }
    }

    //Metodo que se ejecuta cuando se escribe algo en el buscador o se modifica el texto de este
    override fun doFilter(userFilter: Editable?) {
        Handler(Looper.getMainLooper()).post {

            //Obtenemos la lista filtrada con los elementos que coinciden total o parcialmente
            //con el texto del buscador
            pantryFiltered = pantryMutableList.filter { purchase ->
                purchase.name.lowercase().contains(userFilter.toString().lowercase())
            }.toMutableList()

            //actualizamos el adapter
            pantryAdapter.updatePantryList(pantryFiltered)
        }
    }

    //Metodo que obtiene el tipo de moneda actual de la App
    override fun getCurrentCurrency(): String {
        return pantryListModel.getCurrentCurrency()
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = pantryListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que se ejecuta cuando se clica en uno de los elementos de la lista
    private fun onItemSelected(pantryList: PantryList) {
        pantryListFragment.onClickPantryElement(pantryList.id)
    }

    //Metodo que se ejecuta cuando se pulsa el boton eliminar(papelera) del elemento
    //de la lista
    private fun onDeleteItem(position: Int, pantry: PantryList) {
        pantryListModel.deletePantry(pantry.id)
            .observe(pantryListFragment.viewLifecycleOwner) { result ->
                if (result.status == com.myfood.constants.Constant.OK) {
                    var count = 0
                    var pos: Int = 0
                    pantryMutableList.forEach {
                        if (it.id == pantry.id) {
                            pos = count
                        }
                        count += 1
                    }
                    pantryMutableList.removeAt(pos)
                    if (pantryFiltered.isNotEmpty()) pantryFiltered.removeAt(position)
                    pantryAdapter.notifyItemRemoved(position)
                }
            }

    }

    //Metodo que se ejecuta cuando se pulsa el boton actulizar( texto con lapiz) del
    //elemento de la lista
    private fun onUpdateItem(pantryList: PantryList) {
        pantryListFragment.onUpdatePantry(pantryList.id)
    }
}
