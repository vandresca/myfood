package com.myfood.mvp.expiration

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R

class ExpirationListAdapter(
    private var expirationList: List<ExpirationList>,
    private var currency: String
) : RecyclerView.Adapter<ExpirationListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
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

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: ExpirationListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = expirationList[position]

        //Llamamos al view holder y le pasamos el elemento y el tipo de moneda
        holder.render(item, currency)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = expirationList.size

    //Obtenemos la suma de precios de todos los elementos de la lista
    fun getTotalPrice(): Double {
        return expirationList.sumOf { expiration -> expiration.price.toDouble() }
    }

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updateExpirationList(updatedExpirationList: List<ExpirationList>) {

        //Actualizamos la lista de elementos
        this.expirationList = updatedExpirationList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}