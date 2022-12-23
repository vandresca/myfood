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
    private lateinit var pantryFeaturePresenter: PantryFeaturePresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()

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

        //Creamos el presentador
        pantryFeaturePresenter = PantryFeaturePresenter(this, requireContext())

        //Obtenemos el idioma de la App y establecemos las traducciones
        mutableTranslations = pantryFeaturePresenter.getTranslationsScreen()
        setTranslations()

        //Obtenemos los atributos del producto de despensa
        pantryFeaturePresenter.getPantryProduct(idPantry)

        //Inicializamos el boton eliminar producto despena
        binding.btnDeletePantryProduct.setOnClickListener {

            //Mostamos un mensaje para confirmar que se desea eliminar el producto
            Popup.showConfirm(
                requireContext(), resources,
                mutableTranslations[Constant.MSG_DELETE_PANTRY_QUESTION]!!,
                mutableTranslations[Constant.BTN_YES]!!,
                mutableTranslations[Constant.BTN_NO]!!
            ) {
                //En caso afirmativo se borra y se vuelve a la pantalla de Despensa
                pantryFeaturePresenter.deletePantry(idPantry)

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

    //Se ejecuta cuando se ha eliminado un producrto de despensa de la base de datos
    override fun onDeletePantryProduct(){
        loadFragment(PantryListFragment())
    }

    //Se ejecuta cuando se cargan los atributos del producto despensa
    override fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity) {

        //Verificamos que la respuesta es correcta
        if (pantryProductEntity.status == Constant.OK) {

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
            mutableTranslations[Constant.LABEL_BARCODE]!!
        binding.lBrand.text =
            mutableTranslations[Constant.LABEL_BRAND]!!
        binding.lPlace.text =
            mutableTranslations[Constant.LABEL_PLACE]!!
        binding.lPrice.text =
            mutableTranslations[Constant.LABEL_PRICE]!!
        binding.lQuantity.text =
            mutableTranslations[Constant.LABEL_QUANTITY]!!
        binding.lQuantityUnit.text =
            mutableTranslations[Constant.LABEL_QUANTITY_UNIT]!!
        binding.lWeight.text =
            mutableTranslations[Constant.LABEL_WEIGHT]!!
        binding.lExpirationDate.text =
            mutableTranslations[Constant.LABEL_EXPIRATION_DATE]!!
        binding.lPreferenceDate.text =
            mutableTranslations[Constant.LABEL_PREFERENCE_DATE]!!
        binding.btnNutrients.text =
            mutableTranslations[Constant.BTN_NUTRIENTS]!!
    }
}