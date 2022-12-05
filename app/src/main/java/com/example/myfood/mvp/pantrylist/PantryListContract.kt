package com.example.myfood.mvp.pantrylist

import android.text.Editable

interface PantryListContract {
    interface View {
        fun onUserIdLoaded(idUser: String)
        fun showUpdatePurchaseScreen(idPurchase: String)
        fun initRecyclerView(purchaseAdapter: PantryListAdapter)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getPantryList(application: PantryListPresenter, idUser: String)
        fun deletePantry(id: String)
        fun getUserId(application: PantryListFragment)
    }
}