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
    private val onClickUpdate: (PantryList) -> Unit,
    private val currency: String
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
        holder.render(item, onClickListener, onClickDelete, onClickUpdate, currency)
    }

    override fun getItemCount(): Int = purchaseList.size

    fun getTotalPrice(): Double {
        return purchaseList.sumOf { pantry -> pantry.price.toDouble() }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePantryList(updatedPantryList: List<PantryList>) {
        this.purchaseList = updatedPantryList
        notifyDataSetChanged()
    }
}