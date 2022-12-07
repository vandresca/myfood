package com.example.myfood.mvp.quantityunit

import android.text.Editable
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation

interface QuantityUnitListContract {
    interface View {
        fun loadData(places: List<QuantityUnit>)
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(placeListAdapter: QuantityUnitListAdapter)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
    }

    interface Presenter {
        fun loadData(places: List<QuantityUnit>)
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getQuantityUnitList(application: QuantityUnitListFragment)
        fun deleteQuantityUnit(idQuantityUnit: String)
        fun updateQuantityUnit(idQuantityUnit: String)
        fun addQuantityUnit(quantityUnit: String)
        fun getCurrentLanguage(application: QuantityUnitListFragment)
        fun getTranslations(application: QuantityUnitListFragment, language: Int)

    }
}