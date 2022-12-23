package com.myfood.mvp.shoplist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databinding.ElementShopListBinding


class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //Obtenemos el binding de la vista del item
    val binding = ElementShopListBinding.bind(view)

    //LLenamos los datos del item
    fun render(
        shopListModel: ShopList,
        onCLickDelete: (Int, ShopList) -> Unit,
        onClickUpdate: (ShopList) -> Unit
    ) {
        binding.tvSPName.text = shopListModel.name
        binding.tvSPQuantity.text = shopListModel.quantity
        binding.tvSPQuantityUnit.text = shopListModel.quantityUnit

        //Incializamos los eventos
        binding.btnDeleteSP.setOnClickListener { onCLickDelete(adapterPosition, shopListModel) }
        binding.btnUpdateSP.setOnClickListener { onClickUpdate(shopListModel) }
    }
}