package com.example.myfood.mvp.pantrylist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databinding.ElementPurchaseListBinding


class PantryListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementPurchaseListBinding.bind(view)

    fun render(
        purchaseListModel: PantryList,
        onClickListener: (PantryList) -> Unit,
        onCLickDelete: (Int, PantryList) -> Unit,
        onClickUpdate: (PantryList) -> Unit
    ) {
        binding.tvPPName.text = purchaseListModel.name
        binding.tvPPQuantity.text = purchaseListModel.quantity
        binding.tvPPQuantityUnit.text = purchaseListModel.quantityUnit
        binding.tvPPPrice.text = purchaseListModel.price
        binding.tvPPCurrency.text = purchaseListModel.currency
        binding.itemViewPL.setOnClickListener { onClickListener(purchaseListModel) }
        binding.btnDeletePP.setOnClickListener { onCLickDelete(adapterPosition, purchaseListModel) }
        binding.btnUpdatePP.setOnClickListener { onClickUpdate(purchaseListModel) }
    }
}