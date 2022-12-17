package com.example.myfood.mvp.recipelist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvp.recipe.RecipeFragment
import com.example.myfood.mvvm.data.model.RecipeListEntity

class RecipeListPresenter(
    private val recipeListView: RecipeListContract.View,
    private val recipeListModel: RecipeListContract.Model,
    private val recipeListFragment: RecipeListFragment,
    context: Context
) : RecipeListContract.Presenter {
    private lateinit var recipeAdapter: RecipeListAdapter
    private var recipeMutableList: MutableList<RecipeList> = mutableListOf()
    private lateinit var recipeTitle: String
    private lateinit var idLanguage: String
    fun loadData(idLanguage: String?, recipeTitle: String) {
        this.recipeTitle = recipeTitle
        this.idLanguage = idLanguage!!
        recipeListModel.getRecipeList(idLanguage)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }

    init {
        recipeListModel.getInstance(context)
    }

    override fun loadRecipes(result: RecipeListEntity) {
        if (result.status == Constant.OK) {
            recipeMutableList = result.toMVP().toMutableList()
            initData()
        }
    }

    override fun initData() {
        recipeAdapter = RecipeListAdapter(
            recipeList = recipeMutableList,
            onClickListener = { recipeItem -> onItemSelected(recipeItem) },
        )
        Handler(Looper.getMainLooper()).post {
            recipeListView.initRecyclerView(recipeAdapter)
        }
    }

    override fun getCurrentLanguage(): String {
        return recipeListModel.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = recipeListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    fun filterAll() {
        recipeListModel.getRecipeList(idLanguage)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }

    fun filterSuggested() {
        recipeListModel.getRecipesSuggested(idLanguage)
            .observe(recipeListFragment) { data -> loadRecipes(data) }
    }

    override fun doFilter(userFilter: Editable?) {
        val recipeFiltered = recipeMutableList.filter { recipe ->
            recipe.title.lowercase().contains(userFilter.toString().lowercase())
        }
        recipeAdapter.updateExpirationList(recipeFiltered)
    }

    private fun onItemSelected(recipeList: RecipeList) {
        recipeListView.loadFragment(
            RecipeFragment(recipeList.id, idLanguage)
        )
    }
}
