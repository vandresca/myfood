package com.example.myfood.mvp.recipelist

import android.text.Editable
import androidx.fragment.app.Fragment
import com.example.myfood.databasesqlite.entity.Translation

interface RecipeListContract {
    interface View {
        fun initRecyclerView(recipeListAdapter: RecipeListAdapter)
        fun loadFragment(fragment: Fragment)
        fun onUserIdLoaded(idUser: String)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
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
        fun getCurrentLanguage(application: RecipeListFragment)
        fun getTranslations(application: RecipeListFragment, language: Int)
    }
}