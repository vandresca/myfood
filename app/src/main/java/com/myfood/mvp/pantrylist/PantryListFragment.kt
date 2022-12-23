package com.myfood.mvp.pantrylist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databinding.PantryListFragmentBinding
import com.myfood.mvp.addpantryproduct.AddPantryFragment
import com.myfood.mvp.optionaddpantry.OptionAddPantryFragment
import com.myfood.mvp.pantryfeature.PantryFeatureFragment

class PantryListFragment : Fragment(), PantryListContract.View {

    //Decalración de variables globales
    private var _binding: PantryListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryListPresenter: PantryListPresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Despensa
        _binding = PantryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutPantryList.visibility = View.INVISIBLE

        //Creamos el presentador
        pantryListPresenter = PantryListPresenter(this, requireContext())

        //Inicializamos el buscador
        initSearcher()

        //Obtenemos el idioma de la App y establecemos las traducciones
        mutableTranslations = pantryListPresenter.getTranslationsScreen()
        setTranslations()

        //Inicializar el boton añadir producto despensa
        initAddPantryClick()
    }

    override fun onUpdatePantry(idPurchase: String) {
        //Si se ha pulsado el boton modificar vamos a la pantalla añadir producto
        //despensa en modo modificar y pasandole el id del producto
        loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPurchase))
    }

    override fun onClickPantryElement(idPantry: String) {
        //Si se ha pulsado en un item de la lista se muestra la pantalla de caracteristicas
        //de producto despensa
        loadFragment(PantryFeatureFragment(idPantry))
    }


    private fun initSearcher() {

        //Inicializamos un evento que escuche cada vez que se cambia el campo texto para que
        //llame al metodo dofilter con dicho texto
        binding.etFilterPL.addTextChangedListener { watchText ->
            pantryListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(purchaseAdapter: PantryListAdapter) {

        //Inicializamos el linear layout manager y el adapter del recyclerview
        binding.rvPL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPL.adapter = purchaseAdapter

        //Incializamos la cabecera que va justo encima del recyclerview
        binding.tvPricePLProduct.text = String.format("%.2f", purchaseAdapter.getTotalPrice())
        binding.tvTotalPLProduct.visibility = View.VISIBLE
        binding.tvPricePLProduct.visibility = View.VISIBLE
        binding.tvCurrencyPLProduct.visibility = View.VISIBLE
    }

    private fun initAddPantryClick() {

        //Inicializamos el click al pulsar el boton "+" (añadir) para que vaya a la pantalla
        //para elegir entre escanear o poner el producto a mano
        binding.addPLItem.setOnClickListener {
            loadFragment(OptionAddPantryFragment())
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


    //Establecer traducciones
    @SuppressLint("SetTextI18n")
    override fun setTranslations() {
        binding.layoutPantryList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations[Constant.TITLE_PANTRY_LIST]!!
        binding.etFilterPL.hint = mutableTranslations[Constant.FIELD_SEARCH]!!
        binding.tvTotalPLProduct.text = "${mutableTranslations[Constant.LABEL_TOTAL]!!}:  "
        binding.tvCurrencyPLProduct.text = " ${pantryListPresenter.getCurrentCurrency()}"
    }

}
