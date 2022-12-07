package com.example.myfood.mvp.pantrylist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R

class PantryListAdapter(
    private var purchaseList: List<PantryList>,
    private val onClickListener: (PantryList) -> Unit,
    private val onClickDelete: (Int, PantryList) -> Unit,
    private val onClickUpdate: (PantryList) -> Unit
) : RecyclerView.Adapter<PantryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantryListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PantryListViewHolder(
            layoutInflater.inflate(
                R.layout.element_pantry_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PantryListViewHolder, position: Int) {
        val item = purchaseList[position]
        holder.render(item, onClickListener, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = purchaseList.size

    fun getTotalPrice(): Double {
        return purchaseList.sumOf { purchase -> purchase.price.toDouble() }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePurchaseList(updatedPurchaseList: List<PantryList>) {
        this.purchaseList = updatedPurchaseList
        notifyDataSetChanged()
    }
}