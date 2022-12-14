package com.example.myfood.mvp.shoplist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.ShopListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Compra
interface ShopListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(shopListAdapter: ShopListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun loadData(shopListEntity: ShopListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
        fun getUserId(): String

    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getUserId(): String
        fun getShopList(idUser: String): MutableLiveData<ShopListEntity>
        fun deleteShop(idShop: String): MutableLiveData<SimpleResponseEntity>
    }
}