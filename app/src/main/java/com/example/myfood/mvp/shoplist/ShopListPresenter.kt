package com.example.myfood.mvp.shoplist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.ShopListEntity

class ShopListPresenter(
    private val shopListView: ShopListContract.View,
    private val shopListModel: ShopListContract.Model,
    private val shopListFragment: ShopListFragment,
    context: Context
) : ShopListContract.Presenter {
    private lateinit var shopAdapter: ShopListAdapter
    private lateinit var idUser: String
    private var shopMutableList: MutableList<ShopList> = mutableListOf()
    private var shopFiltered: MutableList<ShopList> = mutableListOf()

    init {
        shopListModel.getInstance(context)
    }

    override fun loadData(shopListEntity: ShopListEntity) {
        if (shopListEntity.status == Constant.OK) {
            shopMutableList = shopListEntity.toMVP().toMutableList()
            initData()
        }
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
        shopFiltered = shopMutableList.filter { shop ->
            shop.name.lowercase().contains(userFilter.toString().lowercase())
        }.toMutableList()
        shopAdapter.updateShopList(shopFiltered)
    }

    override fun getCurrentLanguage(): String {
        return shopListModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = shopListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    override fun getUserId(): String {
        return shopListModel.getUserId()
    }

    private fun onItemSelected(shopList: ShopList) {
        print(shopList.name)
    }

    private fun onDeleteItem(position: Int, shop: ShopList) {
        shopListModel.deleteShop(shop.id).observe(shopListFragment.viewLifecycleOwner) { result ->
            if (result.status == Constant.OK) {
                var count = 0
                var pos = 0
                shopMutableList.forEach {
                    if (it.id == shop.id) {
                        pos = count
                    }
                    count += 1
                }
                shopMutableList.removeAt(pos)

                if (shopFiltered.isNotEmpty()) shopFiltered.removeAt(position)

                shopAdapter.notifyItemRemoved(position)
            }
        }
    }

    private fun onUpdateItem(shopList: ShopList) {
        shopListView.showUpdateShopProductScreen(shopList.id)
    }

    fun setIdUser(lifecycleOwner: LifecycleOwner, idUser: String) {
        this.idUser = idUser
        shopListModel.getShopList(idUser).observe(lifecycleOwner) { data -> loadData(data) }
    }

}
