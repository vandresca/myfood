package com.example.myfood.mvp.shoplist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.ShopListEntity

interface ShopListContract {
    interface View : Translatable.View {
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(shopListAdapter: ShopListAdapter)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun loadData(shopListEntity: ShopListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
        fun getUserId(): String

    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getUserId(): String
        fun getShopList(idUser: String): MutableLiveData<ShopListEntity>
        fun deleteShop(idShop: String)
    }
}