package com.myfood.mvp.pantryfeature


import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.mvp.pantryfeature.PantryNutrientFragment
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.PantryFeatureFragmentBinding
import com.myfood.mvp.addpantryproduct.AddPantryFragment
import com.myfood.mvp.pantrylist.PantryListFragment
import com.myfood.popup.Popup
import java.util.*


class PantryFeatureFragment(private var idPantry: String) : Fragment(),
    PantryFeatureContract.View {

    //Declaración de variables globales
    private var _binding: PantryFeatureFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryProduct: PantryProductEntity
    private lateinit var pantryFeatureModel: PantryFeatureModel
    private lateinit var pantryFeaturePresenter: PantryFeaturePresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Caracteristica Producto Despensa
        _binding = PantryFeatureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutPantryFeature.visibility = View.INVISIBLE

        //Creamos el modelo
        pantryFeatureModel = PantryFeatureModel()

        //Creamos el presentador
        pantryFeaturePresenter = PantryFeaturePresenter(this, pantryFeatureModel, requireContext())

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = pantryFeaturePresenter.getCurrentLanguage()
        this.mutableTranslations = pantryFeaturePresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Obtenemos los atributos del producto de despensa
        pantryFeaturePresenter.getPantryProduct(idPantry).observe(this.viewLifecycleOwner)
        { product -> onLoadPantryFeature(product) }

        //Inicializamos el boton eliminar producto despena
        binding.btnDeletePantryProduct.setOnClickListener {

            //Mostamos un mensaje para confirmar que se desea eliminar el producto
            Popup.showConfirm(
                requireContext(), resources,
                mutableTranslations?.get(com.myfood.constants.Constant.MSG_DELETE_PANTRY_QUESTION)!!.text,
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_YES)!!.text,
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_NO)!!.text
            ) {
                //En caso afirmativo se borra y se vuelve a la pantalla de Despensa
                pantryFeaturePresenter.deletePantry(idPantry)
                loadFragment(PantryListFragment())
            }
        }

        //Inicializamos el boton modificar producto para que vaya a la pantalla añadir producto
        //despensa en modo modificar
        binding.btnUpdatePantryProduct.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPantry))
        }

        //Inicializamos el boton Nutrientes para que vaya a la pantalla Productos Despena Nutrientes
        binding.btnNutrients.setOnClickListener {
            loadFragment(PantryNutrientFragment(pantryProduct))
        }

    }


    //Se ejecuta cuando se cargan los atributos del producto despensa
    override fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity) {

        //Verificamos que la respuesta es correcta
        if (pantryProductEntity.status == com.myfood.constants.Constant.OK) {

            //Asignamos el producto recibido a una variable global
            pantryProduct = pantryProductEntity

            //Si la imagen no esta vacia la asignamos al ImageView
            if (pantryProduct.image.isNotEmpty()) {
                val decoded = Base64.getDecoder().decode(pantryProduct.image)
                val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
                binding.ivProduct.setImageBitmap(bitmap)
            }

            //Asignamos los valores del resto de campos
            binding.header.titleHeader.text = pantryProduct.name
            binding.leExpirationDate.text = pantryProduct.expiredDate
            binding.lePreferenceDate.text = pantryProduct.preferenceDate
            binding.leBarcode.text = pantryProduct.barcode
            binding.leQuantity.text = pantryProduct.quantity
            binding.leQuantityUnit.text = pantryProduct.quantityUnit
            binding.lePlace.text = pantryProduct.storePlace
            binding.leWeight.text = pantryProduct.weight
            binding.lePrice.text = pantryProduct.price
            binding.leBrand.text = pantryProduct.brand
            binding.layoutPantryFeature.visibility = View.VISIBLE
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

    //Establece las traducciones
    override fun setTranslations() {
        binding.lBarcode.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_BARCODE)!!.text
        binding.lBrand.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_BRAND)!!.text
        binding.lPlace.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_PLACE)!!.text
        binding.lPrice.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_PRICE)!!.text
        binding.lQuantity.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_QUANTITY)!!.text
        binding.lQuantityUnit.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_QUANTITY_UNIT)!!.text
        binding.lWeight.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_WEIGHT)!!.text
        binding.lExpirationDate.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_EXPIRATION_DATE)!!.text
        binding.lPreferenceDate.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_PREFERENCE_DATE)!!.text
        binding.btnNutrients.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_NUTRIENTS)!!.text
    }
}