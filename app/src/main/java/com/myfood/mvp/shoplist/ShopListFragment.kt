package com.myfood.mvp.shoplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.ShopListFragmentBinding
import com.myfood.mvp.addshopproduct.AddShopFragment

class ShopListFragment : Fragment(), ShopListContract.View {

    //Declaración de variables
    private var _binding: ShopListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListPresenter: ShopListPresenter
    private lateinit var shopListModel: ShopListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Lista de productos de compra
        _binding = ShopListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutShopList.visibility = View.INVISIBLE

        //Creamos el modelo
        shopListModel = ShopListModel()

        //Creamos el presentador
        shopListPresenter = ShopListPresenter(
            this, ShopListModel(), this,
            requireContext()
        )

        //Obtenemos el id de usuario de la App
        val idUser = shopListPresenter.getUserId()

        //Inicializamos el usuario y cargamos la lista de productos de compra
        shopListPresenter.setIdUser(this, idUser)

        //Obtenemos los atributos del producto de despensa
        val currentLanguage = shopListPresenter.getCurrentLanguage()
        this.mutableTranslations = shopListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Inicializar buscador
        initSearcher()

        //Inicializar boton añadir producto compra
        initAddShopProductClick()

    }


    override fun showUpdateShopProductScreen(idShop: String) {
        //Si se ha pulsado el boto modificar del producto en la lista vamos a la pantalla
        //añadir producto compra con el id de compra
        loadFragment(AddShopFragment(Constant.MODE_UPDATE, idShop))
    }

    private fun initSearcher() {

        //Incializamos el evento que se activa cuando se produce un cambio de texto en
        //el campo de texto y que lanzara el metodo doFilter
        binding.etFilterSL.addTextChangedListener { watchText ->
            shopListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(shopListAdapter: ShopListAdapter) {

        //Inicializamos el linear layout manager y  el adapter del recyclerview
        binding.rvSL.layoutManager = LinearLayoutManager(this.context)
        binding.rvSL.adapter = shopListAdapter
    }

    private fun initAddShopProductClick() {

        //Inicializamos el boton añadir producto de compra para que vaya a la pantalla
        //añadir producto de compra en modo añadir
        binding.addSLItem.setOnClickListener {
            loadFragment(AddShopFragment(com.myfood.constants.Constant.MODE_ADD))
        }
    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    private fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack (sirve para cuando
        //hacemos clic en el back button del movil)
        //Comiteamos
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.layoutShopList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_SHOPPING_LIST)!!.text
        binding.etFilterSL.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SEARCH)!!.text
    }
}