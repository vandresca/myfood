package com.example.myfood.mvp.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.StorePlace

class PlaceListAdapter(
    private var placeList: List<StorePlace>,
    private val onClickDelete: (Int, StorePlace) -> Unit,
    private val onClickUpdate: (StorePlace) -> Unit
) : RecyclerView.Adapter<PlaceListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlaceListViewHolder(
            layoutInflater.inflate(
                R.layout.element_place_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) {
        val item = placeList[position]
        holder.render(item, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = placeList.size

    fun updateShopList(updatedPlaceList: List<StorePlace>) {
        this.placeList = updatedPlaceList
        notifyDataSetChanged()
    }
}