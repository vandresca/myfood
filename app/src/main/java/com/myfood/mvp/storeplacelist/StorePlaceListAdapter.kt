package com.myfood.mvp.storeplacelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R
import com.myfood.databases.databasesqlite.entity.StorePlace

class StorePlaceListAdapter(
    private var placeList: List<StorePlace>,
    private val onClickDelete: (Int, StorePlace) -> Unit,
    private val onClickUpdate: (StorePlace) -> Unit
) : RecyclerView.Adapter<StorePlaceListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorePlaceListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StorePlaceListViewHolder(
            layoutInflater.inflate(
                R.layout.element_place_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StorePlaceListViewHolder, position: Int) {
        val item = placeList[position]
        holder.render(item, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = placeList.size

    fun updateStorePlaceList(updatedPlaceList: List<StorePlace>) {
        this.placeList = updatedPlaceList
        notifyDataSetChanged()
    }
}