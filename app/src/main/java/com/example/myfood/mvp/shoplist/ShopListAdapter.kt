package com.example.myfood.mvp.shoplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R

class ShopListAdapter(
    private var shopList: List<ShopList>,
    private val onClickListener: (ShopList) -> Unit,
    private val onClickDelete: (Int, ShopList) -> Unit,
    private val onClickUpdate: (ShopList) -> Unit
) : RecyclerView.Adapter<ShopListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShopListViewHolder(
            layoutInflater.inflate(
                R.layout.element_shop_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = shopList[position]
        holder.render(item, onClickListener, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = shopList.size

    fun updateShopList(updatedShopList: List<ShopList>) {
        this.shopList = updatedShopList
        notifyDataSetChanged()
    }
}