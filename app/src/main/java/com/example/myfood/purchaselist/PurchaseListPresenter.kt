package com.example.myfood.purchaselist

import android.text.Editable

class PurchaseListPresenter(
    private val purchaseListView: PurchaseListContract.View,
    private val purchaseListModel: PurchaseListContract.Model
) : PurchaseListContract.Presenter {
    private lateinit var purchaseAdapter: PurchaseListAdapter
    private var purchasesMutableList: MutableList<PurchaseList> = mutableListOf()

    override fun initData() {
        purchasesMutableList = purchaseListModel.getList()
        purchaseAdapter = PurchaseListAdapter(
            purchaseList = purchasesMutableList,
            onClickListener = { purchaseItem -> onItemSelected(purchaseItem) },
            onClickDelete = { position -> onDeleteItem(position) },
            onClickUpdate = { purchaseItem -> onUpdateItem(purchaseItem) })
        purchaseListView.initRecyclerView(purchaseAdapter)
    }

    override fun doFilter(userFilter: Editable?) {
        val purchasesFiltered = purchasesMutableList.filter { purchase ->
            purchase.name.lowercase().contains(userFilter.toString().lowercase())
        }
        purchaseAdapter.updatePurchaseList(purchasesFiltered)
    }

    private fun onItemSelected(purchaseList: PurchaseList) {
        print(purchaseList.name)
    }

    private fun onDeleteItem(position: Int) {
        purchasesMutableList.removeAt(position)
        purchaseAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(purchaseList: PurchaseList) {
        purchaseListView.showUpdatePurchaseScreen()
    }

}
