package com.myfood.mvp.optionaddpantry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databinding.OptionAddPantryFragmentBinding
import com.myfood.mvp.addpantryproduct.AddPantryFragment


class OptionAddPantryFragment : Fragment(), OptionAddPantryContract.View {

    //Declaración de variables globales
    private var _binding: OptionAddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()
    private lateinit var optionAddPantryModel: OptionAddPantryModel
    private lateinit var optionAddPantryPresenter: OptionAddPantryPresenter

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Opcion entre escaneo o pantalla Añadir Producto Despensa
        _binding = OptionAddPantryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutOptionAddPantry.visibility = View.INVISIBLE

        //Creamos el modelo
        optionAddPantryModel = OptionAddPantryModel()

        //Creamos el presentador
        optionAddPantryPresenter = OptionAddPantryPresenter(requireContext())

        //Obtenemos el idioma de la App y establecemos las traducciones
        mutableTranslations = optionAddPantryPresenter.getTranslationsScreen()
        setTranslations()

        //Inicializamos el click de escanear para que vaya a la pantalla Añadir Producto Despena
        //con el modo escaneo para que inicie la libreria de escaneo
        binding.btnOptionBarCode.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_SCAN))
        }

        //Inicializamos el click para añadir producto despensa a mano para que vaya a dicha pantalla
        binding.btnOptionKeyboard.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_ADD))
        }
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.layoutOptionAddPantry.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations[Constant.TITLE_ADD_PANTRY]!!
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