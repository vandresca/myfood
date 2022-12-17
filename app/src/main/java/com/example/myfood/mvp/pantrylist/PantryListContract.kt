package com.example.myfood.mvp.pantrylist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.PantryListEntity

interface PantryListContract {
    interface View : Translatable.View {
        fun onUserIdLoaded(idUser: String?)
        fun showUpdatePurchaseScreen(idPurchase: String)
        fun showPantryProduct(idPantry: String)
        fun initRecyclerView(purchaseAdapter: PantryListAdapter)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun loadData(pantryListEntity: PantryListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
        fun getUserId(): String
        fun getPantryList(idUser: String): MutableLiveData<PantryListEntity>
        fun getCurrentCurrency(): String
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun getPantryList(idUser: String): MutableLiveData<PantryListEntity>
        fun getCurrentCurrency(): String
        fun deletePantry(id: String)
        fun getUserId(): String
    }
}