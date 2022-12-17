package com.example.myfood.mvp.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.RecipeFragmentBinding
import com.example.myfood.mvvm.data.model.RecipeEntity

class RecipeFragment(
    private val idRecipe: String,
    private val idLanguage: String,
) : Fragment(), RecipeContract.View {
    private var _binding: RecipeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipePresenter: RecipePresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.layoutRecipe.visibility = View.INVISIBLE
        recipePresenter = RecipePresenter(this, RecipeModel(), requireContext())
        val currentLanguage = recipePresenter.getCurrentLanguage()
        this.mutableTranslations = recipePresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        recipePresenter.getRecipe(idRecipe, idLanguage).observe(this.viewLifecycleOwner)
        { recipe -> onRecipeLoaded(recipe) }
    }

    override fun onRecipeLoaded(recipeEntity: RecipeEntity) {
        if (recipeEntity.status == Constant.OK) {
            binding.layoutRecipe.visibility = View.VISIBLE
            binding.tvRIName.text = recipeEntity.title
            binding.tvRIPortions.text = recipeEntity.portions
            binding.tvRIngredients.text = recipeEntity.ingredients
            binding.tvRIElaboration.text = recipeEntity.directions
        }
    }

    override fun setTranslations() {
        binding.header.titleHeader.text = mutableTranslations?.get(Constant.TITLE_RECIPE)!!.text
        binding.lRIPortions.text = mutableTranslations?.get(Constant.LABEL_PORTIONS)!!.text
        binding.lRIIngredients.text = mutableTranslations?.get(Constant.LABEL_INGREDIENTS)!!.text
        binding.lRIElaboration.text = mutableTranslations?.get(Constant.LABEL_DIRECTIONS)!!.text
    }
}