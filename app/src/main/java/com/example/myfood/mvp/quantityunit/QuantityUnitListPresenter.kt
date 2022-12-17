package com.example.myfood.mvp.quantityunit

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation

class QuantityUnitListPresenter(
    private val quantityUnitListView: QuantityUnitListContract.View,
    private val quantityUnitListModel: QuantityUnitListContract.Model,
    context: Context
) : QuantityUnitListContract.Presenter {
    private lateinit var quantityUnitAdapter: QuantityUnitListAdapter
    private var quantityUnitMutableList: MutableList<QuantityUnit> = mutableListOf()


    init {
        quantityUnitListModel.getInstance(context)
    }


    override fun loadData() {
        quantityUnitMutableList = quantityUnitListModel.getQuantityUnits().toMutableList()
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
        quantityUnitAdapter.updateQuantityUnitList(shopFiltered)
    }

    override fun getCurrentLanguage(): String {
        return quantityUnitListModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = quantityUnitListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    private fun onDeleteItem(position: Int, quantityUnit: QuantityUnit) {
        quantityUnitListModel.deleteQuantityUnit(quantityUnit.idQuantityUnit.toString())
        quantityUnitMutableList.removeAt(position)
        quantityUnitAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(QuantityUnitList: QuantityUnit) {
        quantityUnitListView.showUpdateQuantityUnitScreen(QuantityUnitList)
    }

}
