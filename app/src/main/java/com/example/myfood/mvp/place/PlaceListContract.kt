package com.example.myfood.mvp.place

import android.text.Editable
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation

interface PlaceListContract {
    interface View {
        fun loadData(places: List<StorePlace>)
        fun showUpdateShopProductScreen(idShop: String)
        fun initRecyclerView(placeListAdapter: PlaceListAdapter)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
    }

    interface Presenter {
        fun loadData(places: List<StorePlace>)
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getPlaceList(application: PlaceListFragment)
        fun deleteShop(idPlace: String)
        fun updatePlace(idPlace: String)
        fun addPlace(place: String)
        fun getCurrentLanguage(application: PlaceListFragment)
        fun getTranslations(application: PlaceListFragment, language: Int)

    }
}