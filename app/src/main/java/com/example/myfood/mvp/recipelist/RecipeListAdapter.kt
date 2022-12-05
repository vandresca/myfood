package com.example.myfood.mvp.recipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.R

class RecipeListAdapter(
    private var recipeList: List<RecipeList>,
    private val onClickListener: (RecipeList) -> Unit,
) : RecyclerView.Adapter<RecipeListViewHolder>() {

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

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val item = recipeList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = recipeList.size

    fun updateExpirationList(updatedRecipeList: List<RecipeList>) {
        this.recipeList = updatedRecipeList
        notifyDataSetChanged()
    }
}