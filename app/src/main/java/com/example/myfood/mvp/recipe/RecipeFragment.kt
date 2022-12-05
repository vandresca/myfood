package com.example.myfood.mvp.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.databinding.RecipeFragmentBinding
import com.example.myfood.mvp.recipelist.RecipeList

class RecipeFragment(private val recipe: RecipeList) : Fragment(), RecipeContract.View {
    private var _binding: RecipeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipePresenter: RecipePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.header.titleHeader.text = "Recipe"
        recipePresenter = RecipePresenter(this, RecipeModel(), recipe.id)
    }

    override fun showRecipe(recipe: Recipe) {
        binding.tvRIName.text = recipe.title
        binding.tvRIPortions.text = recipe.portion
        binding.tvRIngredients.text = recipe.ingredients
        binding.tvRIElaboration.text = recipe.elaboration
        binding.tvRIName.visibility = View.VISIBLE
        binding.tvRIPortions.visibility = View.VISIBLE
        binding.lRIPortions.visibility = View.VISIBLE
        binding.tvRIngredients.visibility = View.VISIBLE
        binding.tvRIIngredientsLabel.visibility = View.VISIBLE
        binding.tvRIElaboration.visibility = View.VISIBLE
        binding.tvRIElaborationLabel.visibility = View.VISIBLE
    }
}