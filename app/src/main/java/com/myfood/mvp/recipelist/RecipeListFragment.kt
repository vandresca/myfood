package com.myfood.mvp.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.R
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.RecipeListFragmentBinding

class RecipeListFragment : Fragment(), RecipeListContract.View {

    //Declaración de variables globales
    private var _binding: RecipeListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeListPresenter: RecipeListPresenter
    private lateinit var recipeListModel: RecipeListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null


    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Lista de recetas
        _binding = RecipeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutRecipeList.visibility = View.INVISIBLE

        //Creamos el modelo
        recipeListModel = RecipeListModel()

        //Creamos el presentador
        recipeListPresenter = RecipeListPresenter(this, RecipeListModel(), this, requireContext())

        //Obtenemos el usuario de la App
        val user = recipeListPresenter.getUserId()

        //Obtenemos los atributos del producto de despensa
        val currentLanguage = recipeListPresenter.getCurrentLanguage()
        this.mutableTranslations = recipeListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Obtenemos la lista de recetas
        recipeListPresenter.loadData(
            currentLanguage,
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_RECIPE)!!.text
        )

        //Inicializamos el buscador
        initSearcher()

        //Inicializamos el click del boton Todos para que muestre todas las recetas
        binding.btnRLAll.setOnClickListener { recipeListPresenter.filterAll() }

        //Inicializamos el click del boton sugerencias para que muestre solo las recetas
        //sugeridas
        binding.btnRLSuggestions.setOnClickListener { recipeListPresenter.filterSuggested(user) }
    }

    override fun initRecyclerView(recipeListAdapter: RecipeListAdapter) {

        //Inicializar el linear layout manager y el adapter del recyclerview
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = recipeListAdapter
    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    override fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack (sirve para cuando
        //hacemos clic en el back button del movil)
        //Comiteamos
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //Establecer las traducciones
    override fun setTranslations() {
        binding.layoutRecipeList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_RECIPE_LIST)!!.text
        binding.etFilterRL.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SEARCH)!!.text
        binding.btnRLAll.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_ALL)!!.text
        binding.btnRLSuggestions.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_SUGGESTIONS)!!.text
    }


    private fun initSearcher() {

        //Inicializamos un evento que escuche cada vez que cambia del campo de texto del buscador
        //con un nuevo texto y si sucede llamamos al metodo doFilter
        binding.etFilterRL.addTextChangedListener { watchText ->
            recipeListPresenter.doFilter(watchText)
        }
    }
}