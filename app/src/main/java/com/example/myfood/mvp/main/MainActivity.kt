package com.example.myfood.mvp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.mvp.config.ConfigFragment
import com.example.myfood.mvp.expiration.ExpirationListFragment
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvp.recipelist.RecipeListFragment
import com.example.myfood.mvp.shoplist.ShopListFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        mainPresenter = MainPresenter(this, MainModel(), this)
        val currentLanguage = mainPresenter.getCurrentLanguage()
        setTranslations(mainPresenter.getTranslations(currentLanguage.toInt()))
    }

    override fun setTranslations(translations: MutableMap<String, Translation>?) {
        translations!!.values.forEach {
            when (it.word) {
                Constant.MENU_PANTRY -> binding.bottomNavigation.menu.findItem(R.id.purchaseItem).title =
                    it.text
                Constant.MENU_SHOPPING -> binding.bottomNavigation.menu.findItem(R.id.shopListItem).title =
                    it.text
                Constant.MENU_EXPIRATION -> binding.bottomNavigation.menu.findItem(R.id.expirationItem).title =
                    it.text
                Constant.MENU_RECIPE -> binding.bottomNavigation.menu.findItem(R.id.recipeItem).title =
                    it.text
                Constant.MENU_CONFIG -> binding.bottomNavigation.menu.findItem(R.id.configItem).title =
                    it.text
            }
        }
        loadFragment(PantryListFragment())
        navigate()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        if (supportFragmentManager.fragments.isNotEmpty())
            transaction.addToBackStack(fragment.toString())
        transaction.commit()
    }

    private fun navigate() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.purchaseItem -> {
                    loadFragment(PantryListFragment())
                    true
                }
                R.id.shopListItem -> {
                    loadFragment(ShopListFragment())
                    true
                }
                R.id.expirationItem -> {
                    loadFragment(ExpirationListFragment())
                    true
                }
                R.id.recipeItem -> {
                    loadFragment(RecipeListFragment())
                    true
                }
                R.id.configItem -> {
                    loadFragment(ConfigFragment(binding))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}