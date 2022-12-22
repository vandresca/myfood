package com.myfood.mvp.storeplacelist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databinding.ElementPlaceListBinding


class StorePlaceListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
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