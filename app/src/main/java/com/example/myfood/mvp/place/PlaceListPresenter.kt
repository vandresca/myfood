package com.example.myfood.mvp.place

import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.databasesqlite.entity.StorePlace

class PlaceListPresenter(
    private val placeListView: PlaceListContract.View,
    private val placeListModel: PlaceListContract.Model,
) : PlaceListContract.Presenter {
    private lateinit var placeAdapter: PlaceListAdapter
    private var placeMutableList: MutableList<StorePlace> = mutableListOf()

    override fun loadData(places: List<StorePlace>) {
        places.forEach {
            placeMutableList.add(it)
        }
        initData()
    }

    override fun initData() {
        placeAdapter = PlaceListAdapter(
            placeList = placeMutableList,
            onClickDelete = { position, placeItem -> onDeleteItem(position, placeItem) },
            onClickUpdate = { placeItem -> onUpdateItem(placeItem) })

        Handler(Looper.getMainLooper()).post {
            placeListView.initRecyclerView(placeAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        val shopFiltered = placeMutableList.filter { place ->
            place.storePlace.lowercase().contains(userFilter.toString().lowercase())
        }
        placeAdapter.updateShopList(shopFiltered)
    }

    private fun onDeleteItem(position: Int, place: StorePlace) {
        placeListModel.deleteShop(place.idStorePlace.toString())
        placeMutableList.removeAt(position)
        placeAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(placeList: StorePlace) {
        placeListView.showUpdateShopProductScreen(placeList.idStorePlace.toString())
    }

}
