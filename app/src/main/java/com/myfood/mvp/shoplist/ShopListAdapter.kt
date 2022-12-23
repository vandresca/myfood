package com.myfood.mvp.shoplist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R

class ShopListAdapter(
    private var shopList: List<ShopList>,
    private val onClickDelete: (Int, ShopList) -> Unit,
    private val onClickUpdate: (ShopList) -> Unit
) : RecyclerView.Adapter<ShopListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
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

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = shopList[position]

        //Llamamos al view holder y le pasamos el elemento y los eventos
        holder.render(item, onClickDelete, onClickUpdate)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = shopList.size

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updateShopList(updatedShopList: List<ShopList>) {

        //Actualizamos la lista de elementos
        this.shopList = updatedShopList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}