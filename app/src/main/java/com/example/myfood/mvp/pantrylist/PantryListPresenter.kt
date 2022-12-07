package com.example.myfood.mvp.pantrylist

import android.os.Handler
import android.os.Looper
import android.text.Editable
import org.json.JSONObject

class PantryListPresenter(
    private val purchaseListView: PantryListContract.View,
    private val purchaseListModel: PantryListContract.Model,
    idUser: String
) : PantryListContract.Presenter {
    private lateinit var purchaseAdapter: PantryListAdapter
    private var purchasesMutableList: MutableList<PantryList> = mutableListOf()

    init {
        purchaseListModel.getPantryList(idUser) { data -> loadData(data) }
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response!!)
        val products = json.getJSONArray("products")
        val purchaseList: ArrayList<PantryList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            purchaseList.add(
                PantryList(
                    item.get("id").toString(),
                    item.get("name").toString(),
                    item.get("quantity").toString(),
                    item.get("quantityUnit").toString(),
                    item.get("price").toString(),
                    "€"
                )
            )
        }
        purchasesMutableList = purchaseList.toMutableList()
        initData()
    }


    override fun initData() {
        purchaseAdapter = PantryListAdapter(
            purchaseList = purchasesMutableList,
            onClickListener = { purchaseItem -> onItemSelected(purchaseItem) },
            onClickDelete = { position, purchaseItem -> onDeleteItem(position, purchaseItem) },
            onClickUpdate = { purchaseItem -> onUpdateItem(purchaseItem) })
        Handler(Looper.getMainLooper()).post {
            purchaseListView.initRecyclerView(purchaseAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        Handler(Looper.getMainLooper()).post {
            val purchasesFiltered = purchasesMutableList.filter { purchase ->
                purchase.name.lowercase().contains(userFilter.toString().lowercase())
            }
            purchaseAdapter.updatePurchaseList(purchasesFiltered)
        }
    }

    private fun onItemSelected(purchaseList: PantryList) {
        print(purchaseList.name)
    }

    private fun onDeleteItem(position: Int, purchase: PantryList) {
        purchaseListModel.deletePantry(purchase.id)
        purchasesMutableList.removeAt(position)
        purchaseAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(purchaseList: PantryList) {
        purchaseListView.showUpdatePurchaseScreen(purchaseList.id)
    }
}
