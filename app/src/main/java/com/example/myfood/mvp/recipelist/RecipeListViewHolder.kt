package com.example.myfood.mvp.recipelist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databinding.ElementRecipeListBinding


class RecipeListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementRecipeListBinding.bind(view)

    fun render(
        recipeListModel: RecipeList,
        onClickListener: (RecipeList) -> Unit,
    ) {
        binding.tvRecipeName.text = recipeListModel.title
        binding.itemViewRecipe.setOnClickListener { onClickListener(recipeListModel) }
    }
}