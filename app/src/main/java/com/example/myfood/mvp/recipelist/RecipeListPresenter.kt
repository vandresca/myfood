package com.example.myfood.mvp.recipelist

import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.mvp.recipe.RecipeFragment
import org.json.JSONObject

class RecipeListPresenter(
    private val recipeListView: RecipeListContract.View,
    private val recipeListModel: RecipeListContract.Model,
    private var idUser: String
) : RecipeListContract.Presenter {
    private lateinit var recipeAdapter: RecipeListAdapter
    private var recipeMutableList: MutableList<RecipeList> = mutableListOf()

    init {
        recipeListModel.getRecipeList(this, "1")
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response)
        val products = json.getJSONArray("recipes")
        val recipeList: ArrayList<RecipeList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            recipeList.add(
                RecipeList(
                    item.get("id").toString(),
                    item.get("title").toString(),
                )
            )
        }
        recipeMutableList = recipeList.toMutableList()
        initData()
    }

    override fun loadSuggested(response: String?) {
        val json = JSONObject(response)
        val products = json.getJSONArray("recipes")
        val recipeList: ArrayList<RecipeList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            recipeList.add(
                RecipeList(
                    item.get("id").toString(),
                    item.get("title").toString(),
                )
            )
        }
        recipeMutableList = recipeList.toMutableList()
        initData()
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

    fun filterAll() {
        recipeListModel.getRecipeList(this, "1")
    }

    fun filterSuggested() {
        recipeListModel.getRecipesSuggested(this, "1")
    }

    override fun doFilter(userFilter: Editable?) {
        val recipeFiltered = recipeMutableList.filter { recipe ->
            recipe.title.lowercase().contains(userFilter.toString().lowercase())
        }
        recipeAdapter.updateExpirationList(recipeFiltered)
    }

    private fun onItemSelected(recipeList: RecipeList) {
        recipeListView.loadFragment(RecipeFragment(recipeList))
        //print(recipeList.name)
    }
}
