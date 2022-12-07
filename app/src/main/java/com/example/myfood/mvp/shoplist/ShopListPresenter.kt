package com.example.myfood.mvp.shoplist

import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.constants.Constant
import org.json.JSONObject

class ShopListPresenter(
    private val shopListView: ShopListContract.View,
    private val shopListModel: ShopListContract.Model,
    idUser: String
) : ShopListContract.Presenter {
    private lateinit var shopAdapter: ShopListAdapter
    private var shopMutableList: MutableList<ShopList> = mutableListOf()

    init {
        shopListModel.getShopList(idUser) { data -> loadData(data) }
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response!!)
        val products = json.getJSONArray(Constant.JSON_PRODUCTS)
        val shopList: ArrayList<ShopList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            shopList.add(
                ShopList(
                    item.get(Constant.JSON_ID).toString(),
                    item.get(Constant.JSON_NAME).toString(),
                    item.get(Constant.JSON_QUANTITY).toString(),
                    item.get(Constant.JSON_QUANTITY_UNIT).toString()
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
