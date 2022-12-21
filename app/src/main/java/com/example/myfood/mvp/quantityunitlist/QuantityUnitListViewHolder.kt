package com.example.myfood.mvp.quantityunitlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databinding.ElementQuantityUnitListBinding


class QuantityUnitListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementQuantityUnitListBinding.bind(view)

    fun render(
        quantityUnitListModel: QuantityUnit,
        onCLickDelete: (Int, QuantityUnit) -> Unit,
        onClickUpdate: (QuantityUnit) -> Unit
    ) {
        binding.tvEQU.text = quantityUnitListModel.quantityUnit
        binding.btnDeleteEQU.setOnClickListener {
            onCLickDelete(
                adapterPosition,
                quantityUnitListModel
            )
        }
        binding.btnUpdateEQU.setOnClickListener { onClickUpdate(quantityUnitListModel) }
    }
}