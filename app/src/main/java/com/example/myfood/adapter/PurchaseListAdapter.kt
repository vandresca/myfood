package com.example.myfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.entity.PurchaseList
import com.example.pec1.R

class PurchaseListAdapter(
    private var purchaseList: List<PurchaseList>,
    private val onClickListener: (PurchaseList) -> Unit,
    private val onClickDelete: (Int) -> Unit,
    private val onClickUpdate: (PurchaseList) -> Unit
) : RecyclerView.Adapter<PurchaseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PurchaseListViewHolder(
            layoutInflater.inflate(
                R.layout.element_purchase_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PurchaseListViewHolder, position: Int) {
        val item = purchaseList[position]
        holder.render(item, onClickListener, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = purchaseList.size

    fun updatePurchaseList(updatedPurchaseList: List<PurchaseList>) {
        this.purchaseList = updatedPurchaseList
        notifyDataSetChanged()
    }
}