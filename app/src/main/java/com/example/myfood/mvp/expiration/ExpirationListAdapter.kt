package com.example.myfood.mvp.expiration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R

class ExpirationListAdapter(
    private var expirationList: List<ExpirationList>,
) : RecyclerView.Adapter<ExpirationListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExpirationListViewHolder(
            layoutInflater.inflate(
                R.layout.element_expiration_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExpirationListViewHolder, position: Int) {
        val item = expirationList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = expirationList.size

    fun updateExpirationList(updatedExpirationList: List<ExpirationList>) {
        this.expirationList = updatedExpirationList
        notifyDataSetChanged()
    }
}