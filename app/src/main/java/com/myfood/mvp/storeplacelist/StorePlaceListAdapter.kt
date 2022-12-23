package com.myfood.mvp.storeplacelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R
import com.myfood.databases.databasesqlite.entity.StorePlace

class StorePlaceListAdapter(
    private var placeList: List<StorePlace>,
    private val onClickDelete: (Int, StorePlace) -> Unit,
    private val onClickUpdate: (StorePlace) -> Unit
) : RecyclerView.Adapter<StorePlaceListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorePlaceListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StorePlaceListViewHolder(
            layoutInflater.inflate(
                R.layout.element_place_list,
                parent,
                false
            )
        )
    }

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: StorePlaceListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = placeList[position]

        //Llamamos al view holder y le pasamos el elemento y los eventos
        holder.render(item, onClickDelete, onClickUpdate)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = placeList.size

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updateStorePlaceList(updatedPlaceList: List<StorePlace>) {

        //Actualizamos la lista de elementos
        this.placeList = updatedPlaceList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}