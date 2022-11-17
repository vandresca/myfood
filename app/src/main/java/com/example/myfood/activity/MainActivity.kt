package com.example.myfood.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myfood.fragment.*
import com.example.pec1.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var header: TextView
    lateinit var bottomNav: BottomNavigationView

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
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
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