package com.myfood.mvp.shoplist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.myfood.databases.databasemysql.entity.ShopListEntity

class ShopListPresenter(
    private val shopListFragment: ShopListFragment,
    context: Context
) : ShopListContract.Presenter {

    //Declaración de variables globales
    private val shopListModel: ShopListModel = ShopListModel()
    private lateinit var shopAdapter: ShopListAdapter
    private var idUser: String
    private var shopMutableList: MutableList<ShopList> = mutableListOf()
    private var shopFiltered: MutableList<ShopList> = mutableListOf()
    private var currentLanguage: String

    init {

        //Creamos las instancias de base de datos
        shopListModel.createInstances(context)

        //Obtenemos el id de usario actual de la App
        idUser = shopListModel.getUserId()

        //Obtenemos el idioma actual de la App
        currentLanguage = shopListModel.getCurrentLanguage()

        //Obtenemos la lista de productos de compra
        shopListModel.getShopList(idUser).
        observe(shopListFragment) { data -> loadData(data) }

    }

    //Metodo que se ejecuta cuando disponemos de la lista de productos de compra
    override fun loadData(shopListEntity: ShopListEntity) {
        //Verificamos que la respuesta es correcta
        if (shopListEntity.status == com.myfood.constants.Constant.OK) {

            //Transformamos el objeto ShopListEntity a una lista de objetos
            //ShopList
            shopMutableList = shopListEntity.toMVP().toMutableList()

            //Creamos el adapter para el recycler view
            shopAdapter = ShopListAdapter(
                shopList = shopMutableList,
                onClickDelete = { position, shopItem -> onDeleteItem(position, shopItem) },
                onClickUpdate = { shopItem -> onUpdateItem(shopItem) })

            //Inicializamos el recyclerview
            Handler(Looper.getMainLooper()).post {
                shopListFragment.initRecyclerView(shopAdapter)
            }
        }
    }

    //Metodo que se ejecuta cuando introducimos texto o lo modificamos en el campo de texto del
    //buscador
    override fun doFilter(userFilter: Editable?) {

        //Guardamos la lista filtrada y la transformamos a mutable
        shopFiltered = shopMutableList.filter { shop ->
            shop.name.lowercase().contains(userFilter.toString().lowercase())
        }.toMutableList()

        //Refrescamos los cambios en el recyclerview
        shopAdapter.updateShopList(shopFiltered)
    }

    //Metodo que devuelve las traducciones de la pantalla
    override fun getTranslationsScreen():MutableMap<String, String>{
        val mutableTranslations: MutableMap<String, String> =
            mutableMapOf()
        val translations = shopListModel.getTranslations(currentLanguage.toInt())
        translations.forEach {
            mutableTranslations[it.word] = it.text
        }
        return mutableTranslations
    }

    //Metodo que elimina un producto de compra de la lista y base de datos
    private fun onDeleteItem(position: Int, shop: ShopList) {

        //Llamamos a la API de borrar producto compra
        shopListModel.deleteShop(shop.id).observe(shopListFragment)
        { result ->

            //Comprobamos que la respuesta es correcta
            if (result.status == com.myfood.constants.Constant.OK) {

                //Obtenemos la posicion del elemento en la lista total
                var count = 0
                var pos = 0
                shopMutableList.forEach {
                    if (it.id == shop.id) {
                        pos = count
                    }
                    count += 1
                }

                //Eliminamos el elemento de la lista total
                shopMutableList.removeAt(pos)

                //Si la lista no esta vacia (hemos buscado algo) eliminamos de la lista
                //filtrada el elemento eliminada
                if (shopFiltered.isNotEmpty()) shopFiltered.removeAt(position)

                //Notificamos los cambios para que se refresque el recyclerview
                shopAdapter.notifyItemRemoved(position)
            }
        }
    }

    //Metodo que se ejecuta cuando hacemos click en modificar producto en el item de la lista
    private fun onUpdateItem(shopList: ShopList) {
        shopListFragment.onUpdateShopProduct(shopList.id)
    }
}
