package com.myfood.mvp.recipelist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databinding.ElementRecipeListBinding


class RecipeListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //Obtenemos el binding de la vista del item
    val binding = ElementRecipeListBinding.bind(view)

    //LLenamos los datos del item
    fun render(
        recipeListModel: RecipeList,
        onClickListener: (RecipeList) -> Unit,
    ) {
        binding.tvRecipeName.text = recipeListModel.title

        //Incializamos los eventos
        binding.itemViewRecipe.setOnClickListener { onClickListener(recipeListModel) }
    }
}