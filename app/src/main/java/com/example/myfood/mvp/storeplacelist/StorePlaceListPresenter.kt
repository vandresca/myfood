package com.example.myfood.mvp.storeplacelist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.databases.databasesqlite.entity.StorePlace
import com.example.myfood.databases.databasesqlite.entity.Translation

class StorePlaceListPresenter(
    private val placeListView: StorePlaceListContract.View,
    private val placeListModel: StorePlaceListContract.Model,
    private val context: Context
) : StorePlaceListContract.Presenter {
    private lateinit var placeAdapter: StorePlaceListAdapter
    private var placeMutableList: MutableList<StorePlace> = mutableListOf()
    private var storePlaceFiltered: MutableList<StorePlace> = mutableListOf()

    init {
        placeListModel.getInstance(context)
    }

    override fun loadData() {
        val places = placeListModel.getStorePlaces()
        placeMutableList = mutableListOf()
        places.forEach {
            placeMutableList.add(it)
        }
        initData()
    }

    override fun initData() {
        placeAdapter = StorePlaceListAdapter(
            placeList = placeMutableList,
            onClickDelete = { position, placeItem -> onDeleteItem(position, placeItem) },
            onClickUpdate = { placeItem -> onUpdateItem(placeItem) })

        Handler(Looper.getMainLooper()).post {
            placeListView.initRecyclerView(placeAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        storePlaceFiltered = placeMutableList.filter { place ->
            place.storePlace.lowercase().contains(userFilter.toString().lowercase())
        }.toMutableList()
        placeAdapter.updateStorePlaceList(storePlaceFiltered)
    }

    override fun getCurrentLanguage(): String {
        return placeListModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = placeListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    private fun onDeleteItem(position: Int, place: StorePlace) {
        placeListModel.deleteStorePlace(place.idStorePlace.toString())

        var count = 0
        var pos = 0
        placeMutableList.forEach {
            if (it.idStorePlace == place.idStorePlace) {
                pos = count
            }
            count += 1
        }
        placeMutableList.removeAt(pos)
        if (storePlaceFiltered.isNotEmpty()) storePlaceFiltered.removeAt(position)
        placeAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(storePlaceList: StorePlace) {
        placeListView.showUpdateStorePlaceScreen(storePlaceList)
    }

}
