package com.example.myfood.mvp.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.RecipeListFragmentBinding

class RecipeListFragment : Fragment(), RecipeListContract.View {
    private var _binding: RecipeListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeListPresenter: RecipeListPresenter
    private lateinit var recipeListModel: RecipeListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        recipeListModel.getInstance(requireContext())
        recipeListModel.getUserId(this)
        recipeListModel.getCurrentLanguage(this)
    }

    override fun onUserIdLoaded(idUser: String) {
        recipeListPresenter = RecipeListPresenter(this, RecipeListModel(), idUser)
        initSearcher()
        binding.btnRLAll.setOnClickListener { recipeListPresenter.filterAll() }
        binding.btnRLSuggestions.setOnClickListener { recipeListPresenter.filterSuggested() }
    }

    private fun initSearcher() {
        binding.etFilterRL.addTextChangedListener { watchText ->
            recipeListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(recipeAdapter: RecipeListAdapter) {
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = recipeAdapter
    }

    override fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslations.put(it.word, it)
        }
        setTranslations()
    }

    private fun setTranslations() {
        binding.layoutRecipeList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["recipeTitle"]!!.text
        binding.etFilterRL.hint = mutableTranslations["search"]!!.text
        binding.btnRLAll.text = mutableTranslations["all"]!!.text
        binding.btnRLSuggestions.text = mutableTranslations["suggestions"]!!.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        recipeListModel.getTranslations(this, language.toInt())
    }
}