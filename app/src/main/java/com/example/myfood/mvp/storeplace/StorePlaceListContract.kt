package com.example.myfood.mvp.storeplace

import android.content.Context
import android.text.Editable
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.interfaces.Translatable

interface StorePlaceListContract {
    interface View : Translatable.View {
        fun showUpdateStorePlaceScreen(storePlaceToUpdate: StorePlace)
        fun initRecyclerView(storePlacesAdapter: StorePlaceListAdapter)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun loadData()
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    interface Model : Translatable.Model {
        fun getInstance(application: Context)
        fun getStorePlaces(): List<StorePlace>
        fun deleteStorePlace(idPlace: String)
    }
}