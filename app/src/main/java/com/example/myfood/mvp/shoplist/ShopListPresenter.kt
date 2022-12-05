package com.example.myfood.mvp.shoplist

import android.os.Handler
import android.os.Looper
import android.text.Editable
import org.json.JSONObject

class ShopListPresenter(
    private val shopListView: ShopListContract.View,
    private val shopListModel: ShopListContract.Model,
    idUser: String
) : ShopListContract.Presenter {
    private lateinit var shopAdapter: ShopListAdapter
    private var shopMutableList: MutableList<ShopList> = mutableListOf()

    init {
        shopListModel.getShopList(this, idUser)
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response)
        val products = json.getJSONArray("products")
        val shopList: ArrayList<ShopList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            shopList.add(
                ShopList(
                    item.get("id").toString(),
                    item.get("name").toString(),
                    item.get("quantity").toString(),
                    item.get("quantityUnit").toString()
                )
            )
        }
        shopMutableList = shopList.toMutableList()
        initData()
    }

    override fun initData() {
        shopAdapter = ShopListAdapter(
            shopList = shopMutableList,
            onClickListener = { shopItem -> onItemSelected(shopItem) },
            onClickDelete = { position, shopItem -> onDeleteItem(position, shopItem) },
            onClickUpdate = { shopItem -> onUpdateItem(shopItem) })

        Handler(Looper.getMainLooper()).post {
            shopListView.initRecyclerView(shopAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        val shopFiltered = shopMutableList.filter { shop ->
            shop.name.lowercase().contains(userFilter.toString().lowercase())
        }
        shopAdapter.updateShopList(shopFiltered)
    }

    private fun onItemSelected(shopList: ShopList) {
        print(shopList.name)
    }

    private fun onDeleteItem(position: Int, shop: ShopList) {
        shopListModel.deleteShop(shop.id)
        shopMutableList.removeAt(position)
        shopAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(shopList: ShopList) {
        shopListView.showUpdateShopProductScreen(shopList.id)
    }

}
