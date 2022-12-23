package com.myfood.mvp.quantityunitlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R
import com.myfood.databases.databasesqlite.entity.QuantityUnit

class QuantityUnitListAdapter(
    private var quantityUnitList: List<QuantityUnit>,
    private val onClickDelete: (Int, QuantityUnit) -> Unit,
    private val onClickUpdate: (QuantityUnit) -> Unit
) : RecyclerView.Adapter<QuantityUnitListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
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

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: QuantityUnitListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = quantityUnitList[position]

        //Llamamos al view holder y le pasamos el elemento y el tipo de moneda
        holder.render(item, onClickDelete, onClickUpdate)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = quantityUnitList.size

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updateQuantityUnitList(updatedQuantityUnitList: List<QuantityUnit>) {

        //Actualizamos la lista de elementos
        this.quantityUnitList = updatedQuantityUnitList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}