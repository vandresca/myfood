package com.example.myfood.mvp.shoplist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databinding.ElementShopListBinding


class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementShopListBinding.bind(view)

    fun render(
        shopListModel: ShopList,
        onClickListener: (ShopList) -> Unit,
        onCLickDelete: (Int, ShopList) -> Unit,
        onClickUpdate: (ShopList) -> Unit
    ) {
        binding.tvSPName.text = shopListModel.name
        binding.tvSPQuantity.text = shopListModel.quantity
        binding.tvSPQuantityUnit.text = shopListModel.quantityUnit
        binding.itemViewSL.setOnClickListener { onClickListener(shopListModel) }
        binding.btnDeleteSP.setOnClickListener { onCLickDelete(adapterPosition, shopListModel) }
        binding.btnUpdateSP.setOnClickListener { onClickUpdate(shopListModel) }
    }
}