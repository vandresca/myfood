package com.myfood.mvp.quantityunitlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databinding.ElementQuantityUnitListBinding


class QuantityUnitListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //Obtenemos el binding de la vista del item
    val binding = ElementQuantityUnitListBinding.bind(view)

    //LLenamos los datos del item
    fun render(
        quantityUnitListModel: QuantityUnit,
        onCLickDelete: (Int, QuantityUnit) -> Unit,
        onClickUpdate: (QuantityUnit) -> Unit
    ) {
        binding.tvEQU.text = quantityUnitListModel.quantityUnit

        //Incializamos los eventos
        binding.btnDeleteEQU.setOnClickListener {
            onCLickDelete(adapterPosition, quantityUnitListModel)
        }
        binding.btnUpdateEQU.setOnClickListener { onClickUpdate(quantityUnitListModel) }
    }
}