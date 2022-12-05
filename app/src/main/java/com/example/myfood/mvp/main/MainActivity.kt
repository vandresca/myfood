package com.example.myfood.mvp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.mvp.config.ConfigFragment
import com.example.myfood.mvp.expiration.ExpirationListFragment
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvp.recipelist.RecipeListFragment
import com.example.myfood.mvp.shoplist.ShopListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        MainModel.getInstance(this)
        MainModel.getCurrentLanguage(this)
    }

    fun onCurrentLanguageLoaded(language: String) {
        MainModel.getTranslations(this, Integer.parseInt(language))
    }

    fun onTranslationsLoaded(translations: List<Translation>) {
        translations.forEach {
            when (it.word) {
                "menuPurchase" -> binding.bottomNavigation.menu.findItem(R.id.purchaseItem).title =
                    it.text
                "menuShopping" -> binding.bottomNavigation.menu.findItem(R.id.shopListItem).title =
                    it.text
                "menuExpiration" -> binding.bottomNavigation.menu.findItem(R.id.expirationItem).title =
                    it.text
                "menuRecipe" -> binding.bottomNavigation.menu.findItem(R.id.recipeItem).title =
                    it.text
                "menuConfig" -> binding.bottomNavigation.menu.findItem(R.id.configItem).title =
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