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
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.PantryListFragmentBinding
import com.myfood.mvp.addpantryproduct.AddPantryFragment
import com.myfood.mvp.optionaddpantry.OptionAddPantryFragment
import com.myfood.mvp.pantryfeature.PantryFeatureFragment

class PantryListFragment : Fragment(), PantryListContract.View {

    //Decalración de variables globales
    private var _binding: PantryListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryListPresenter: PantryListPresenter
    private lateinit var pantryListModel: PantryListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

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

        //Creamos el modelo
        pantryListModel = PantryListModel()

        //Creamos el presentador
        pantryListPresenter = PantryListPresenter(
            this,
            pantryListModel, this, requireContext()
        )

        //Obtenemos el id de Usuario
        val idUser = pantryListPresenter.getUserId()

        //Pasamos el id de usuario al presentador para que obtenga la lista
        pantryListPresenter.setIdUser(idUser)


        initSearcher()
        val currentLanguage = pantryListPresenter.getCurrentLanguage()
        this.mutableTranslations = pantryListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Inicializar el boton añadir producto despensa
        initAddPantryClick()
    }

    override fun showUpdatePantryScreen(idPurchase: String) {
        //Si se ha pulsado el boton modificar vamos a la pantalla añadir producto
        //despensa en modo modificar y pasandole el id del producto
        loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPurchase))
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

    override fun showPantryProduct(idPantry: String) {
        //Si se ha pulsado en un item de la lista se muestra la pantalla de caracteristicas
        //de producto despensa
        loadFragment(PantryFeatureFragment(idPantry))
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
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_PANTRY_LIST)!!.text
        binding.etFilterPL.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SEARCH)!!.text
        binding.tvTotalPLProduct.text =
            "${mutableTranslations?.get(com.myfood.constants.Constant.LABEL_TOTAL)!!.text}:  "
        binding.tvCurrencyPLProduct.text = " ${pantryListPresenter.getCurrentCurrency()}"
    }


}
