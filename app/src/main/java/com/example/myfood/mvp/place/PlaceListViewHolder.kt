package com.example.myfood.mvp.place

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databinding.ElementPlaceListBinding


class PlaceListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementPlaceListBinding.bind(view)

    fun render(
        placeListModel: StorePlace,
        onCLickDelete: (Int, StorePlace) -> Unit,
        onClickUpdate: (StorePlace) -> Unit
    ) {
        binding.tvEPlaceName.text = placeListModel.storePlace
        binding.btnDeleteEPlace.setOnClickListener {
            onCLickDelete(
                adapterPosition,
                placeListModel
            )
        }
        binding.btnUpdateEPlace.setOnClickListener { onClickUpdate(placeListModel) }
    }
}