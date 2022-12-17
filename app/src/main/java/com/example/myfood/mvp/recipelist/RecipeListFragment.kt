package com.example.myfood.mvp.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.RecipeListFragmentBinding

class RecipeListFragment : Fragment(), RecipeListContract.View {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeListPresenter: RecipeListPresenter
    private lateinit var recipeListModel: RecipeListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.layoutRecipeList.visibility = View.INVISIBLE
        recipeListModel = RecipeListModel()
        recipeListPresenter = RecipeListPresenter(this, RecipeListModel(), this, requireContext())
        val currentLanguage = recipeListPresenter.getCurrentLanguage()
        this.mutableTranslations = recipeListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        recipeListPresenter.loadData(
            currentLanguage,
            mutableTranslations?.get(Constant.TITLE_RECIPE)!!.text
        )
        initSearcher()
        binding.btnRLAll.setOnClickListener { recipeListPresenter.filterAll() }
        binding.btnRLSuggestions.setOnClickListener { recipeListPresenter.filterSuggested() }


    }

    override fun initRecyclerView(recipeListAdapter: RecipeListAdapter) {
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = recipeListAdapter
    }

    override fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun setTranslations() {
        binding.layoutRecipeList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(Constant.TITLE_RECIPE_LIST)!!.text
        binding.etFilterRL.hint = mutableTranslations?.get(Constant.FIELD_SEARCH)!!.text
        binding.btnRLAll.text = mutableTranslations?.get(Constant.BTN_ALL)!!.text
        binding.btnRLSuggestions.text = mutableTranslations?.get(Constant.BTN_SUGGESTIONS)!!.text
    }

    private fun initSearcher() {
        binding.etFilterRL.addTextChangedListener { watchText ->
            recipeListPresenter.doFilter(watchText)
        }
    }
}