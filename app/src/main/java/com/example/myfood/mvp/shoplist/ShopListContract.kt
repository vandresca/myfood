package com.example.myfood.mvp.shoplist

import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import com.example.myfood.interfaces.Translatable

interface ShopListContract {
    interface View : Translatable.View {
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(shopListAdapter: ShopListAdapter)
        fun onUserIdLoaded(idUser: String)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    interface Model : Translatable.Model {
        fun getUserId(application: LifecycleOwner, callback: (String) -> Unit)
        fun getShopList(idUser: String, callback: (String?) -> Unit)
        fun deleteShop(idShop: String)
    }
}