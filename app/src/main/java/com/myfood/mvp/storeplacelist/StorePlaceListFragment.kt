package com.myfood.mvp.storeplacelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.StorePlaceListFragmentBinding
import com.myfood.mvp.addstoreplace.AddStorePlaceFragment

class StorePlaceListFragment : Fragment(), StorePlaceListContract.View {

    //Declaración de variables
    private var _binding: StorePlaceListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeListPresenter: StorePlaceListPresenter
    private lateinit var placeListModel: StorePlaceListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Lista de lugares de almacenaje
        _binding = StorePlaceListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutPlaceList.visibility = View.INVISIBLE

        //Creamos el modelo
        placeListModel = StorePlaceListModel()

        //Creamos el presentador
        placeListPresenter = StorePlaceListPresenter(this, placeListModel, requireContext())

        //Obtenemos el id de usuario de la App
        val currentLanguage = placeListPresenter.getCurrentLanguage()
        this.mutableTranslations = placeListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Cargamos la lista de lugares de almacenaje
        placeListPresenter.loadData()

        //Inicializamos el boton añadir lugar de almacenaje
        initAddUpdateStorePlaceClick()

        //Inicializamos el buscador
        initSearcher()
    }


    private fun initSearcher() {

        //Incializamos el evento que se activa cuando se produce un cambio de texto en
        //el campo de texto y que lanzara el metodo doFilter
        binding.etFilterPlaceL.addTextChangedListener { watchText ->
            placeListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(storePlacesAdapter: StorePlaceListAdapter) {

        //Inicializamos el linear layout manager y  el adapter del recyclerview
        binding.rvPlaceL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPlaceL.adapter = storePlacesAdapter
    }

    private fun initAddUpdateStorePlaceClick() {

        //Inicializamos el boton "+" (añadir) para que cuando se pulse vaya a la
        //pantalla añadir lugar de almacenaje en modo añadir
        binding.addPlaceLItem.setOnClickListener {
            loadFragment(AddStorePlaceFragment(Constant.MODE_ADD))
        }
    }

    override fun showUpdateStorePlaceScreen(storePlaceToUpdate: StorePlace) {

        //Si se pulsa el boton modificar en el item de la lista se ira a la pantalla
        //añadir lugar de almacenaje en modo actualizar pasando el id
        loadFragment(
            AddStorePlaceFragment(
                com.myfood.constants.Constant.MODE_UPDATE,
                storePlaceToUpdate
            )
        )
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
        binding.layoutPlaceList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_STORE_PLACES_LIST)!!.text
        binding.etFilterPlaceL.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SEARCH)!!.text
    }
}