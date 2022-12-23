package com.myfood.mvp.pantrylist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R

class PantryListAdapter(
    private var purchaseList: List<PantryList>,
    private val onClickListener: (PantryList) -> Unit,
    private val onClickDelete: (Int, PantryList) -> Unit,
    private val onClickUpdate: (PantryList) -> Unit,
    private val currency: String
) : RecyclerView.Adapter<PantryListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
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

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: PantryListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = purchaseList[position]

        //Llamamos al view holder y le pasamos el elemento, los eventos y el tipo de moneda
        holder.render(item, onClickListener, onClickDelete, onClickUpdate, currency)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = purchaseList.size

    //Obtenemos la suma de precios de todos los elementos de la lista
    fun getTotalPrice(): Double {
        return purchaseList.sumOf { pantry -> pantry.price.toDouble() }
    }

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updatePantryList(updatedPantryList: List<PantryList>) {

        //Actualizamos la lista de elementos
        this.purchaseList = updatedPantryList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}