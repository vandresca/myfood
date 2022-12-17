package com.example.myfood.mvp.recipelist

import android.content.Context
import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.RecipeListEntity

interface RecipeListContract {
    interface View : Translatable.View {
        fun initRecyclerView(recipeListAdapter: RecipeListAdapter)
        fun loadFragment(fragment: Fragment)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun loadRecipes(result: RecipeListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getRecipeList(language: String): MutableLiveData<RecipeListEntity>
        fun getRecipesSuggested(language: String): MutableLiveData<RecipeListEntity>
    }
}