package com.example.myfood.mvp.quantityunit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit

class QuantityUnitListAdapter(
    private var quantityUnitList: List<QuantityUnit>,
    private val onClickDelete: (Int, QuantityUnit) -> Unit,
    private val onClickUpdate: (QuantityUnit) -> Unit
) : RecyclerView.Adapter<QuantityUnitListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuantityUnitListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuantityUnitListViewHolder(
            layoutInflater.inflate(
                R.layout.element_quantity_unit_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuantityUnitListViewHolder, position: Int) {
        val item = quantityUnitList[position]
        holder.render(item, onClickDelete, onClickUpdate)
    }

    override fun getItemCount(): Int = quantityUnitList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateQuantityUnitList(updatedQuantityUnitList: List<QuantityUnit>) {
        this.quantityUnitList = updatedQuantityUnitList
        notifyDataSetChanged()
    }
}