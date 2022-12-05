package com.example.myfood.mvp.recipelist

import android.text.Editable
import androidx.fragment.app.Fragment

interface RecipeListContract {
    interface View {
        fun initRecyclerView(recipeListAdapter: RecipeListAdapter)
        fun loadFragment(fragment: Fragment)
        fun onUserIdLoaded(idUser: String)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun loadSuggested(response: String?)
        fun initData()
        fun doFilter(watchText: Editable?)
    }

    interface Model {
        fun getRecipeList(application: RecipeListPresenter, language: String)
        fun getRecipesSuggested(application: RecipeListPresenter, language: String)
        fun getUserId(application: RecipeListFragment)
    }
}