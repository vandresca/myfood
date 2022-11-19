package com.example.myfood.mvp.purchaselist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databinding.ElementPurchaseListBinding


class PurchaseListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementPurchaseListBinding.bind(view)

    fun render(
        purchaseListModel: PurchaseList,
        onClickListener: (PurchaseList) -> Unit,
        onCLickDelete: (Int) -> Unit,
        onClickUpdate: (PurchaseList) -> Unit
    ) {
        binding.tvPPName.text = purchaseListModel.name
        binding.tvPPQuantity.text = purchaseListModel.quantity
        binding.tvPPQuantityUnit.text = purchaseListModel.unit
        binding.tvPPPrice.text = purchaseListModel.price
        binding.tvPPCurrency.text = purchaseListModel.currency
        binding.itemViewPL.setOnClickListener { onClickListener(purchaseListModel) }
        binding.btnDeletePP.setOnClickListener { onCLickDelete(adapterPosition) }
        binding.btnUpdatePP.setOnClickListener { onClickUpdate(purchaseListModel) }
    }
}