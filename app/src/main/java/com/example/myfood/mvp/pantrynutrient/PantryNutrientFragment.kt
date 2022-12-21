package com.example.myfood.mvp.pantryfeature

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.PantryNutrientFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.popup.Popup
import java.util.*


class PantryNutrientFragment(
    private val pantryProduct: PantryProductEntity,
) : Fragment(),
    PantryNutrientContract.View {

    //Declaracion de variables globales
    private var _binding: PantryNutrientFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var nutrientsGroup: List<String>
    private lateinit var pantryNutrientModel: PantryNutrientModel
    private lateinit var pantryNutrientPresenter: PantryNutrientPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var currentLanguage: String

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Nutrientes Producto Despensa
        _binding = PantryNutrientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutPantryNutrient.visibility = View.INVISIBLE

        //Creamos el modelo
        pantryNutrientModel = PantryNutrientModel()

        //Creamos el presentador
        pantryNutrientPresenter =
            PantryNutrientPresenter(this, pantryNutrientModel, requireContext())

        //Obtenemos los atributos del producto de despensa
        currentLanguage = pantryNutrientPresenter.getCurrentLanguage()
        this.mutableTranslations = pantryNutrientPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Establecemos la imagen del producto
        setImage()

        //Obtenemos el grupo de tipo de nutrientes
        pantryNutrientPresenter.getNutrients(currentLanguage).observe(this.viewLifecycleOwner)
        { groupNutrients -> onNutrientsLoaded(groupNutrients) }

        //Obtenemo los nutrientes para el tipo General en el idioma de la aplicación
        pantryNutrientPresenter.getNutrientsByType(
            Constant.GENERAL_NUTRIENT,
            pantryProduct.idFood, currentLanguage
        )
            .observe(this.viewLifecycleOwner)
            { nutrients -> onNutrientsTypeLoaded(nutrients) }


        //Inicializamos el click del boton eliminar
        binding.btnDeletePantryProduct.setOnClickListener {

            //Preguntamos por confirmación para borrar el producto
            Popup.showConfirm(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_DELETE_PANTRY_QUESTION)!!.text,
                mutableTranslations?.get(Constant.BTN_YES)!!.text,
                mutableTranslations?.get(Constant.BTN_NO)!!.text
            ) {
                //Si la respuesta es si, borramos y vamos a la pantalla despensa
                pantryNutrientPresenter.deletePantry(pantryProduct.idPantry)
                loadFragment(PantryListFragment())
            }
        }

        //Inicializamos el click del boton modificar para que vaya a la pantalla añadir despensa
        //en modo actualizar pasandole el id del producto
        binding.btnUpdatePantryProduct.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_UPDATE, pantryProduct.idPantry))
        }

        //Inicializamos el click del boton caracteristicas para que vaya a la pantalla
        //caracteristicas de producto despensa
        binding.btnCharacteristics.setOnClickListener {
            loadFragment(PantryFeatureFragment(pantryProduct.idPantry))
        }

    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack (sirve para cuando
        //hacemos clic en el back button del movil)
        //Comiteamos
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onNutrientsLoaded(nutrientGEntity: NutrientGroupEntity) {

        //Verificamos que la respuesta es correcta
        if (nutrientGEntity.status == Constant.OK) {

            //Guardamos el grupo de tipos de nutriente en una variable global
            nutrientsGroup = nutrientGEntity.nutrients

            //Creamos un adapter y poblamos con los tipos de nutrientes
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                nutrientsGroup
            ).also { adapter ->
                // Especificamos el layout a usar cuando la lista de opciones aparece
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Asignamos el adapter al spinner
                binding.sGroupNutrients.adapter = adapter
                binding.sGroupNutrients.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            //Llamamos al metodo con la posicion seleccionada
                            selectGroupNutrient(position)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

    private fun selectGroupNutrient(position: Int) {

        val nutrientType = position + 1

        //Obtenemos los nutrientes del tipo seleccionado
        pantryNutrientPresenter.getNutrientsByType(
            nutrientType.toString(), pantryProduct.idFood,
            currentLanguage
        ).observe(this.viewLifecycleOwner)
        { nutrients -> onNutrientsTypeLoaded(nutrients) }
    }



    @SuppressLint("SetTextI18n")
    override fun onNutrientsTypeLoaded(nutrientGTEntity: NutrientListTypeEntity) {

        //Creamos dianmicamente TextViews con los nombres de la columna y su valor
        //de los nutrientes
        val layoutContentNutrient = binding.layoutContentNutrients
        layoutContentNutrient.removeAllViews()
        if (nutrientGTEntity.status == Constant.OK) {
            nutrientGTEntity.foodNutrients.forEach {
                val textView = TextView(requireContext())
                val layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                layoutParams.setMargins(60, 40, 0, 0)
                textView.setTextColor(Color.parseColor(Constant.COLOR_TURQUOISE))
                val text = SpannableString("${it.column}: ${it.value}")
                val boldSpan = StyleSpan(Typeface.BOLD)
                text.setSpan(boldSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textView.text = text
                layoutContentNutrient.addView(textView, layoutParams)
            }
        }
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.header.titleHeader.text = pantryProduct.name
        binding.btnCharacteristics.text =
            mutableTranslations?.get(Constant.BTN_CHARACTERISTICS)!!.text
        binding.layoutPantryNutrient.visibility = View.VISIBLE
    }


    private fun setImage() {

        //Comprobamos que la imagen no sea nula
        if (pantryProduct.image.isNotEmpty()) {

            //Decodificamos la imagen de base64 a bytearray
            val decoded = Base64.getDecoder().decode(pantryProduct.image)

            //Transformamos el bytearray a bitmap y lo asignamos al ImageView
            val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
            binding.ivProduct.setImageBitmap(bitmap)
        }
    }
}