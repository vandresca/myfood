package com.example.myfood.mvp.quantityunit

import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.databasesqlite.entity.QuantityUnit

class QuantityUnitListPresenter(
    private val quantityUnitListView: QuantityUnitListContract.View,
    private val quantityUnitListModel: QuantityUnitListContract.Model,
) : QuantityUnitListContract.Presenter {
    private lateinit var quantityUnitAdapter: QuantityUnitListAdapter
    private var quantityUnitMutableList: MutableList<QuantityUnit> = mutableListOf()

    override fun loadData(QuantityUnits: List<QuantityUnit>) {
        QuantityUnits.forEach {
            quantityUnitMutableList.add(it)
        }
        initData()
    }

    override fun initData() {
        quantityUnitAdapter = QuantityUnitListAdapter(
            quantityUnitList = quantityUnitMutableList,
            onClickDelete = { position, QuantityUnitItem ->
                onDeleteItem(
                    position,
                    QuantityUnitItem
                )
            },
            onClickUpdate = { QuantityUnitItem -> onUpdateItem(QuantityUnitItem) })

        Handler(Looper.getMainLooper()).post {
            quantityUnitListView.initRecyclerView(quantityUnitAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        val shopFiltered = quantityUnitMutableList.filter { QuantityUnit ->
            QuantityUnit.quantityUnit.lowercase().contains(userFilter.toString().lowercase())
        }
        quantityUnitAdapter.updateShopList(shopFiltered)
    }

    private fun onDeleteItem(position: Int, QuantityUnit: QuantityUnit) {
        quantityUnitListModel.deleteQuantityUnit(QuantityUnit.idQuantityUnit.toString())
        quantityUnitMutableList.removeAt(position)
        quantityUnitAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(QuantityUnitList: QuantityUnit) {
        quantityUnitListView.showUpdateShopProductScreen(QuantityUnitList.idQuantityUnit.toString())
    }

}
