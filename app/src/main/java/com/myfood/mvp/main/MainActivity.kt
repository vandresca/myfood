package com.myfood.mvp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.ActivityMainBinding
import com.myfood.mvp.config.ConfigFragment
import com.myfood.mvp.expiration.ExpirationListFragment
import com.myfood.mvp.pantrylist.PantryListFragment
import com.myfood.mvp.recipelist.RecipeListFragment
import com.myfood.mvp.shoplist.ShopListFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    //Declaración de variables globales
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainModel: MainModel
    private lateinit var mainPresenter: MainPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Metodo onCreate que se ejecuta cuando la vista esta creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Login
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creamos el modelo
        mainModel = MainModel()

        //Creamos el presentador
        mainPresenter = MainPresenter(this, mainModel, this)

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = mainPresenter.getCurrentLanguage()
        this.mutableTranslations = mainPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Establecemos la lógica del menu de navegación
        navigate()

        //Establecemos que por defecto se cargue la pantalla de Despensa
        loadFragment(PantryListFragment())
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.bottomNavigation.menu.findItem(R.id.purchaseItem).title =
            mutableTranslations?.get(com.myfood.constants.Constant.MENU_PANTRY)!!.text
        binding.bottomNavigation.menu.findItem(R.id.shopListItem).title =
            mutableTranslations?.get(com.myfood.constants.Constant.MENU_SHOPPING)!!.text
        binding.bottomNavigation.menu.findItem(R.id.expirationItem).title =
            mutableTranslations?.get(com.myfood.constants.Constant.MENU_EXPIRATION)!!.text
        binding.bottomNavigation.menu.findItem(R.id.recipeItem).title =
            mutableTranslations?.get(com.myfood.constants.Constant.MENU_RECIPE)!!.text
        binding.bottomNavigation.menu.findItem(R.id.configItem).title =
            mutableTranslations?.get(com.myfood.constants.Constant.MENU_CONFIG)!!.text
    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    private fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack si el framento no esta vacio
        //Comiteamos
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        if (supportFragmentManager.fragments.isNotEmpty())
            transaction.addToBackStack(fragment.toString())
        transaction.commit()
    }

    private fun navigate() {
        //Inicializamos los clicks del menu de navegación para que dependiendo de cual sea
        //vaya a la pantalla correspondiente
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