package com.example.myfood.mvp.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.mvp.config.ConfigFragment
import com.example.myfood.mvp.expiration.ExpirationFragment
import com.example.myfood.mvp.purchaselist.PurchaseListFragment
import com.example.myfood.mvp.recipe.RecipeFragment
import com.example.myfood.mvp.shoplist.ShopListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var header: TextView
    private lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        header = findViewById(R.id.title_header)
        bottomNav = findViewById(R.id.bottom_navigation)
        loadFragment(PurchaseListFragment())
        header.text = "Purchase List"
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
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.purchaseItem -> {
                    loadFragment(PurchaseListFragment())
                    header.text = "Purchase List"
                    true
                }
                R.id.shopListItem -> {
                    loadFragment(ShopListFragment())
                    header.text = "Compra"
                    true
                }
                R.id.expirationItem -> {
                    loadFragment(ExpirationFragment())
                    header.text = "Caducidad"
                    true
                }
                R.id.recipeItem -> {
                    loadFragment(RecipeFragment())
                    header.text = "Recetas"
                    true
                }
                R.id.configItem -> {
                    loadFragment(ConfigFragment())
                    header.text = "Configuración"
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}