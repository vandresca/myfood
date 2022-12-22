package com.myfood.mvp.addshopproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.constants.Constant.Companion.MODE_ADD
import com.myfood.databases.databasemysql.entity.ShopProductEntity
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.AddShopFragmentBinding
import com.myfood.mvp.shoplist.ShopListFragment
import com.myfood.popup.Popup


class AddShopFragment(private val mode: Int, private var idShop: String = "") : Fragment(),
    AddShopContract.View {

    //Declaración de variables globales
    private var _binding: AddShopFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addShopModel: AddShopModel
    private lateinit var addShopPresenter: AddShopPresenter
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private var mutableTranslations: MutableMap<String, Translation>? = null

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Añadir Producto Compra
        _binding = AddShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutAddShop.visibility = View.INVISIBLE

        //Creamos el modelo
        addShopModel = AddShopModel()

        //Creamos el presentador
        addShopPresenter = AddShopPresenter(this, addShopModel, requireContext())

        //Obtenemos el id de usuario de la App y lo asignamos a una variable global
        userId = addShopPresenter.getUserId()

        //Obtenemos las unidades de cantidad de la App y las asignamos al combo
        setQuantities(addShopPresenter.getQuantitiesUnit())

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = addShopPresenter.getCurrentLanguage()
        this.mutableTranslations = addShopPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        //Si venimos del modo modidicar cargamos los datos
        if (mode == com.myfood.constants.Constant.MODE_UPDATE) {
            addShopPresenter.getShopProduct(idShop).observe(this.viewLifecycleOwner)
            { data -> onLoadShopToUpdate(data) }
        }

        //Inicializamos el click del boton añadir
        binding.btnASProduct.setOnClickListener { addUpdateShopToDB() }
    }

    //Se ejecuta cada vez que se viene del modo modificar el producto de compra
    override fun onLoadShopToUpdate(shopProductEntity: ShopProductEntity) {

        //Verificamos que la respuesta es correcta
        if (shopProductEntity.status == com.myfood.constants.Constant.OK) {

            //Cargamos los campos de texto y combo
            binding.etASName.setText(shopProductEntity.name)
            binding.etASQuantity.setText(shopProductEntity.quantity)
            binding.sASQuantityUnit.setSelection(
                quantitiesUnitMutable.indexOf(shopProductEntity.quantityUnit)
            )
        }
    }

    //Se ejecuta una vez se recuperan todas las unidades de cantidad de la App
    private fun setQuantities(quantitiesUnit: List<QuantityUnit>) {

        //Creamos una lista mutable y la cargamos con el String de cada uno de los
        //objetos QuantityUnit.  Luego la asignamos a una variable global
        quantitiesUnitMutable = mutableListOf()
        quantitiesUnit.forEach {
            this.quantitiesUnitMutable.add(it.quantityUnit)
        }

        //Creamos un adapter y lo poblamos con dicha lista
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            quantitiesUnitMutable
        ).also { adapter ->

            //Especificamos el layout a usar cuando la lista de opciones aparece
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Aplicamos el adapter al spinner
            binding.sASQuantityUnit.adapter = adapter
        }
    }

    //Método que añade un producto de compra a la basde de datos
    private fun addUpdateShopToDB() {

        //Recuperamos los valores de los campos de texto y combo
        val name = binding.etASName.text.toString()
        val quantity = binding.etASQuantity.text.toString()
        val quantityUnit = binding.sASQuantityUnit.selectedItem.toString()

        //Si el nombre no está vacio añadimos o actualizamos según el caso
        //Despues volvemos a la pantalla de Compra
        if (name.isNotEmpty()) {
            if (mode == MODE_ADD) {
                addShopPresenter.insertShop(name, quantity, quantityUnit, userId)
                    .observe(this.viewLifecycleOwner) { result ->
                        if (result.status == com.myfood.constants.Constant.OK) {
                            loadFragment(ShopListFragment())
                        }
                    }
            } else {
                addShopPresenter.updateShop(name, quantity, quantityUnit, idShop)
                    .observe(this.viewLifecycleOwner) { result ->
                        if (result.status == com.myfood.constants.Constant.OK) {
                            loadFragment(ShopListFragment())
                        }
                    }
            }
            //En caso contrario indicamos al usuario que debe ponerlo
        } else {
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(com.myfood.constants.Constant.MSG_NAME_REQUIRED)!!.text
            )
        }
    }

    //Establecemos las traducciones
    override fun setTranslations() {
        binding.layoutAddShop.visibility = View.VISIBLE
        binding.lASName.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_NAME)!!.text
        binding.lASQuantity.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_QUANTITY)!!.text
        binding.lASQuantityUnit.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_QUANTITY_UNIT)!!.text
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations?.get(com.myfood.constants.Constant.TITLE_ADD_SHOPPING)!!.text
            binding.btnASProduct.text =
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_ADD_SHOPPING)!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations?.get(com.myfood.constants.Constant.TITLE_UPDATE_SHOPPING)!!.text
            binding.btnASProduct.text =
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_UPDATE_SHOPPING)!!.text
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
}