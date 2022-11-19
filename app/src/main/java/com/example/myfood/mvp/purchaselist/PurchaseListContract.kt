package com.example.myfood.mvp.purchaselist

import android.text.Editable

interface PurchaseListContract {
    interface View {
        fun showUpdatePurchaseScreen()

        //fun searcher(purchaseMutableList: MutableList<PurchaseList>,
        //           purchaseListAdapter: PurchaseListAdapter)
        fun initRecyclerView(purchaseAdapter: PurchaseListAdapter)
    }

    interface Presenter {
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getList(): ArrayList<PurchaseList>
    }
}