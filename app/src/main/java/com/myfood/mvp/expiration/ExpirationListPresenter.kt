package com.myfood.mvp.expiration

import android.content.Context
import android.text.Editable
import com.myfood.databases.databasemysql.entity.ExpirationListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.enum.ExpirationType


class ExpirationListPresenter(
    private val expirationListFragment: ExpirationListFragment,
    context: Context
) : ExpirationListContract.Presenter {

    //Declaración de variables globales
    private lateinit var expirationAdapter: ExpirationListAdapter
    private var expirationMutableList: MutableList<ExpirationList> = mutableListOf()
    private var expirationListModel: ExpirationListModel = ExpirationListModel()
    private var idUser: String

    init {

        //Creamos las instancias de la base de datos
        expirationListModel.createInstances(context)

        //Obtenemos el id de usuario de la App
        this.idUser = expirationListModel.getUserId()

        //Mostramos todos los tipos de caducidad de productos despensa
        filterAll()
    }

    //Metodo que muestra la lista de todos los productos caducados
    fun filterExpired() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_EXPIRED.type, idUser)
            .observe(expirationListFragment) { data -> loadList(data) }
    }

    //Metodo que muestra la lista de todos productos que les falta más de 10 días para caducar
    fun filterMore10days() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_MORE10DAYS.type, idUser)
            .observe(expirationListFragment) { data -> loadList(data) }
    }

    //Metodo que muestra la lista de todos productos entre 0 y 10 días para caducar
    fun filter0to10days() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_OTO10DAYS.type, idUser)
            .observe(expirationListFragment) { data -> loadList(data) }
    }

    //Metodo que muestra todos los tipos de caducidad del producto
    fun filterAll() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_ALL.type, idUser)
            .observe(expirationListFragment) { data -> loadList(data) }
    }

    //Metodo que elimina todos los productos caducados de un usuario
    fun removeExpired() {
        expirationListModel.removeExpired(idUser)
            .observe(expirationListFragment) { response -> onRemovedExpired(response) }
    }

    //Metodo que se ejecuta tras eliminar los productos caducados de un usuario
    override fun onRemovedExpired(result: SimpleResponseEntity) {
        if (result.status == com.myfood.constants.Constant.OK) {
            filterAll()
        }
    }

    //Metodo que se ejecuta despues de lanzar un tipo de filtro de caducidad
    override fun loadList(expirationListEntity: ExpirationListEntity) {

        //Mapeamos los valores del objeto recibido a un objeto de la MVP
        expirationMutableList = expirationListEntity.toMVP().toMutableList()

        //Creamos un adapter personalizado y le pasamos dicha lista y el tipo de moneda actual
        expirationAdapter = ExpirationListAdapter(
            expirationList = expirationMutableList,
            currency = getCurrentCurrency()
        )

        //Incializamos el recyclerview
        //Handler(Looper.getMainLooper()).post {
            expirationListFragment.initRecyclerView(expirationAdapter)
        //}
    }

    //Metodo que se ejecuta cada vez que introducimos o variamos el texto del buscador
    override fun doFilter(userFilter: Editable?) {

        //Filtra en la lista de productos caducados aquellos que coinciden total o parcialmente
        //con el el campo texto del buscador y guarda los elementos resultantes en otra lista
        val expirationFiltered = expirationMutableList.filter { expiration ->
            expiration.name.lowercase().contains(userFilter.toString().lowercase())
        }

        //Actualizamos el adapter con los nuevos elementos para que refresque el recyclerview
        expirationAdapter.updateExpirationList(expirationFiltered)
    }

    //Metodo que obtiene el tipo de moneda actual de la App
    override fun getCurrentCurrency(): String {
        return expirationListModel.getCurrentCurrency()
    }

    //Metodo que obtiene las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val currentLanguage = expirationListModel.getCurrentLanguage()
        val translations = expirationListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

}
