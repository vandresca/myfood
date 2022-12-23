package com.myfood.mvp.recipelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfood.R

class RecipeListAdapter(
    private var recipeList: List<RecipeList>,
    private val onClickListener: (RecipeList) -> Unit,
) : RecyclerView.Adapter<RecipeListViewHolder>() {

    //Metodo onCreateViewHolder
    //Se ejecuta cuando se esta crando la vista del item de la lista del
    //recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeListViewHolder(
            layoutInflater.inflate(
                R.layout.element_recipe_list,
                parent,
                false
            )
        )
    }

    //Metodo onBindViewHolder
    //Se ejecuta cuando ya se ha establecido el binding
    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {

        //Capturamos el elemento del item de la lista
        val item = recipeList[position]

        //Llamamos al view holder y le pasamos el elemento y el evento
        holder.render(item, onClickListener)
    }

    //Metodo que devuelve la cantidad de elementos que existen en la lista
    override fun getItemCount(): Int = recipeList.size

    //Metodo que actualiza la lista de elementos
    @SuppressLint("NotifyDataSetChanged")
    fun updateExpirationList(updatedRecipeList: List<RecipeList>) {

        //Actualizamos la lista de elementos
        this.recipeList = updatedRecipeList

        //Informamos al recycler para que refresque la vista
        notifyDataSetChanged()
    }
}