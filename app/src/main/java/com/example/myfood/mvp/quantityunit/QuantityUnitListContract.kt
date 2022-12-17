package com.example.myfood.mvp.quantityunit

import android.content.Context
import android.text.Editable
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.interfaces.Translatable

interface QuantityUnitListContract {
    interface View : Translatable.View {
        fun showUpdateQuantityUnitScreen(quantityUnitToUpdate: QuantityUnit)
        fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun loadData()
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun getQuantityUnits(): List<QuantityUnit>
        fun deleteQuantityUnit(idQuantityUnit: String)
    }
}