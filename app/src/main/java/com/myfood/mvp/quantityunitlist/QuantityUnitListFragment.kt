package com.myfood.mvp.quantityunitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.constants.Constant.Companion.MODE_ADD
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.QuantityUnitListFragmentBinding
import com.myfood.mvp.addquantityunit.AddQuantityUnitFragment

class QuantityUnitListFragment : Fragment(), QuantityUnitListContract.View {

    //Declaramos las variables globales
    private var _binding: QuantityUnitListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var quantityUnitListPresenter: QuantityUnitListPresenter
    private lateinit var quantityUnitListModel: QuantityUnitListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Unidades de cantidad
        _binding = QuantityUnitListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutQuantityUnitList.visibility = View.INVISIBLE

        //Creamos el modelo
        quantityUnitListModel = QuantityUnitListModel()

        //Creamos el presentador
        quantityUnitListPresenter = QuantityUnitListPresenter(
            this,
            quantityUnitListModel, requireContext()
        )

        //Obtenemos los atributos del producto de despensa
        val currentLanguage = quantityUnitListPresenter.getCurrentLanguage()
        this.mutableTranslations =
            quantityUnitListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Cargamos la lista de unidades de cantidad
        quantityUnitListPresenter.loadData()

        //Inicializamos el boton añadir unidad de cantidad
        initAddUpdateQuantityUnitClick()

        //Inicializamos el buscador
        initSearcher()
    }

    private fun initSearcher() {

        //Incializamos el evento que se activa cuando se produce un cambio de texto en
        //el campo de texto y que lanzara el metodo doFilter
        binding.etFilterQU.addTextChangedListener { watchText ->
            quantityUnitListPresenter.doFilter(watchText)
        }
    }

    override fun showUpdateQuantityUnitScreen(quantityUnitToUpdate: QuantityUnit) {
        //Metodo que se ejecutara al hacer click en el boton modificar iendo
        //a la pantalla añadir cantidad en modo actualizar y pasando el id
        loadFragment(
            AddQuantityUnitFragment(
                Constant.MODE_UPDATE,
                quantityUnitToUpdate
            )
        )
    }

    override fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter) {

        //Inicializamos el linear layout manager y  el adapter del recyclerview
        binding.rvQU.layoutManager = LinearLayoutManager(this.context)
        binding.rvQU.adapter = quantityUnitAdapter
    }

    private fun initAddUpdateQuantityUnitClick() {

        //Inicializamos el boron añadir cantidad para que vaya a la pantalla
        //añadir cantidad en modo añadir
        binding.addQUItem.setOnClickListener {
            loadFragment(AddQuantityUnitFragment(MODE_ADD))
        }
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.layoutQuantityUnitList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_QUANTITY_UNIT_LIST)!!.text

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
}