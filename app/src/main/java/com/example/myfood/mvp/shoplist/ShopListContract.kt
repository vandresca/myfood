package com.example.myfood.mvp.shoplist

import android.text.Editable

interface ShopListContract {
    interface View {
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(shopListAdapter: ShopListAdapter)
        fun onUserIdLoaded(idUser: String)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getUserId(application: ShopListFragment)
        fun getShopList(application: ShopListPresenter, userId: String)
        fun deleteShop(idShop: String)
    }
}