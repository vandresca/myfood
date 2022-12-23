package com.myfood.mvp.addpantryproduct

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.databases.databasemysql.entity.OpenFoodEntity
import com.myfood.databases.databasemysql.entity.PantryProductEntity
import com.myfood.databinding.AddPantryFragmentBinding
import com.myfood.mvp.pantrylist.PantryListFragment
import com.myfood.popup.Popup
import com.myfood.util.DatePickerFragment
import com.myfood.util.UtilTool
import com.squareup.picasso.Picasso
import java.util.*


class AddPantryFragment(private val mode: Int, private var idPantry: String = "") : Fragment(),
    AddPantryContract.View {

    //Declaración de variables globales
    private var _binding: AddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addPantryPresenter: AddPantryPresenter
    private lateinit var mutableTranslations: MutableMap<String, String>

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Añadir Despensa
        _binding = AddPantryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutAddPantry.visibility = View.INVISIBLE

        //Creamos el presentador
        addPantryPresenter = AddPantryPresenter(this, requireContext())

        //Obtenemos las traducciones de pantalla
        mutableTranslations = addPantryPresenter.getTranslationsScreen()
        setTranslations()

        //Inicializamos los combo de unidades de cantidad y lugares de almacenaje
        setQuantitiesUnit()
        setStorePlaces()

        //Incializamos los datpickers, elementos para seleccionar una fecha en el calendario
        initDataPickers()

        //Inicializamos los botones
        initButtons()

        //Aplicamos acciones segun el modo del que venimos
        initDependingMode()
    }

    private fun initDependingMode() {

        //Si venimos del modo SCAN iniciamos el escaner de código de barras
        //Si venimos de modificar (UPDATE) cargamos los datos del producto despensa
        if (mode == Constant.MODE_SCAN) initScanner()
        if (mode == Constant.MODE_UPDATE) {
            addPantryPresenter.getPantryProduct(idPantry)
        }
    }

    // Carga el producto despensa en caso de venir en modo UPDATE en pantalla
    override fun onLoadPantryToUpdate(result: PantryProductEntity) {

        //Verificamos que la respuesta es correcta
        if (result.status == Constant.OK) {

            //Verificamos si la fecha de caducidad no está vacia y si es así la cargamos
            if (result.expiredDate.isNotEmpty())
                binding.etExpirationDate.setText(result.expiredDate)

            //Verificamos si la fecha de preferencia no está vacia y si es así la cargamos
            if (result.preferenceDate.isNotEmpty())
                binding.etPreferenceDate.setText(result.preferenceDate)

            //Cargamos el resto de datos en los campos de texto y combo
            binding.etProductName.setText(result.name)
            binding.etBarcode.setText(result.barcode)
            binding.etQuantity.setText(result.quantity)
            binding.sQuantityUnit.setSelection(
                addPantryPresenter.getPositionQuantitiesUnit(result.quantityUnit))
            binding.sStorePLace.setSelection(
                addPantryPresenter.getPositionStorePlaces(result.storePlace))
            binding.etWeight.setText(result.weight)
            binding.etPrice.setText(result.price)
            binding.etBrand.setText(result.brand)

            //Verificamos si la imagen no está vacia y si es así la cargamos
            if (result.image.isNotEmpty()) {
                binding.ivProduct.setImageBitmap(UtilTool.base64ToBitmap(result.image))
            }
        }

        //Una vez cargados los datos hacemos el layout visible al usuario.
        binding.layoutAddPantry.visibility = View.VISIBLE
    }

    // Método que se ejecuta tras el escaneo del código de barras
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //Obtenemos el código de barras y si no es nulo llamamos al metodo fillProduct
        //para llenar los campos necesarios con los datos del producto de este.
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                binding.etBarcode.setText("")
            } else {
                binding.etBarcode.setText(result.contents)

                //Llamamos al metodo fillProductOpenFood para devolver los atributos del producto
                //alimenticio a partir del código de barras.
                addPantryPresenter.fillProductOpenFood(result.contents)
            }
        } else {
            binding.etBarcode.setText("")
        }
        commit()
    }

    //Se ejecuta tras la llamada de getOpenFoodProduct
    override fun onFillProductOpenFood(result: OpenFoodEntity) {

        //Verificamos que la respuesta es correcta.
        if (result.status == Constant.OK_INT) {

            //Declaramos variables necesarias
            var nameProduct = String()
            var brand = String()
            var srcImage = String()

            //Verificamos que el nombre generico de producto no es nulo ni vacio
            //si es así asignamos el este si no verificamos que el nombre de
            //producto no sea nulo ni vacio y si es asi lo asinamos
            //En caso contrario obtenemos un String vacio
            if (!result.product.genericName.isNullOrEmpty()) {
                nameProduct = result.product.genericName
            } else {
                if (!result.product.productName.isNullOrEmpty()) {
                    nameProduct = result.product.productName
                }
            }

            //Si el texto del nombre de producto es superior a 28
            //caracteres lo acortamos
            if (nameProduct.length > 28) {
                nameProduct = nameProduct.substring(0, 25) + "..."
            }

            //Asignamos el resto de campos. Para la imagen utilizamos la
            //libreria Picasso que nos permite cargar en el ImageView una
            //imagen de Internet a partir de su url.
            if (!result.product.brands.isNullOrEmpty()) {
                brand = result.product.brands
            }
            if (!result.product.image.isNullOrEmpty()) {
                srcImage = result.product.image
            }
            binding.etProductName.setText(nameProduct)
            binding.etBarcode.setText(result.product.barcode)
            binding.etBrand.setText(brand)
            Picasso.get().load(srcImage).into(binding.ivProduct)

        } else {
            //En caso de que la respuesta no sea correcta mostramos
            //un mensaje al usuario indicando que el producto no ha sido
            //hallado.
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations[Constant.MSG_PRODUCT_NOT_FOUND]!!
            )
        }
    }

    private fun initDataPickers() {

        //Inicializamos los botones de los dataPicker
        binding.dpExpirationDate.setOnClickListener {
            //Creamos un dialogo Datapicker
            val dialogDate =
                DatePickerFragment { year, month, day -> showExpirationResult(year, month, day) }
            //Lo mostramos
            dialogDate.show(childFragmentManager, Constant.CONST_DPICKER)
        }
        binding.dpPreferenceDate.setOnClickListener {
            //Creamos un dialogo Datapicker
            val dialogDate =
                DatePickerFragment { year, month, day -> showPreferenceResult(year, month, day) }
            //Lo mostramos
            dialogDate.show(childFragmentManager, Constant.CONST_DPICKER)
        }
    }

    private fun initScanner() {

        //Inicia el escaner de código de barra.
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt(Constant.CONST_SCAN)
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    private fun initButtons() {

        //Inicialiamos el boton de escaneo para que inicie el escaner de
        //código de barras cuando se haga click
        binding.btnScan.setOnClickListener { initScanner() }

        //Inicializamos el boton de Añadir Producto Despensa para que inicie el método de
        //inserción al hacer click.
        binding.btnAddPurchaseProduct.setOnClickListener { addUpdateProductToDB() }

        //Metodo con callback que se ejecuta tras lanzar el Intent y seleccioar una imagen
        //de la galeria de imagen de abajo
        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivProduct.tag = uri
                binding.ivProduct.setImageURI(uri)
            }
        }

        //Inicializamos el boton cambiar imagen para que abra la galeria de imagenes del dispositivo
        //y podamos seleccionar la imagen
        binding.btnChangeImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun addUpdateProductToDB() {
        //Obtenemos los datos de los campos de texto y los almacenamos en una variable
        val barcode = binding.etBarcode.text.toString()
        val name = binding.etProductName.text.toString()
        val quantity = binding.etQuantity.text.toString()
        val quantityUnit = binding.sQuantityUnit.selectedItem.toString()
        val place = binding.sStorePLace.selectedItem.toString()
        val weight = binding.etWeight.text.toString()
        val price = binding.etPrice.text.toString()
        val expirationDate = binding.etExpirationDate.text.toString()
        val preferenceDate = binding.etPreferenceDate.text.toString()
        val brand = binding.etBrand.text.toString()
        val image = UtilTool.bitmapToBase64(binding.ivProduct.drawable.toBitmap())

        //Verificamos que hayamos insertado un mombre de producto y si no mostramos
        //un  mensaje al usuario indicando que lo haga
        if (name.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations[Constant.MSG_NOT_EMPTY_NAME]!!
            )
            //Verificamos que hayamos insertado una fecha de caducidad y si  no mostramos
            //un mensaje al usuario indicando que lo haga
        } else if (expirationDate.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations[Constant.MSG_NOT_EMPTY_EXPIRED_DATE]!!
            )
        } else {
            //Si es correcto y estamos en el modo Actualizar, actualizamos
            //el producto con los datos y cuando recibimos respuesta correcta
            // volvemos a pantalla Despensa
            if (mode != Constant.MODE_UPDATE) {
                addPantryPresenter.insertPantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand
                )

                //Si es correcto y estamos en el modo insertar, insertamos el nuevo
                //producto con los datos y cuando recibimos respuesta correcta volvemos
                //a la pantalla Despensa
            } else {
                addPantryPresenter.updatePantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, idPantry
                )
            }
        }
    }

    //Metodo que se ejecuta tras realizarse correctamente una inserción o actualización
    //de un producto de despensa
    override fun onInsertedOrUpdatedPantry(){
        loadFragment(PantryListFragment())
    }

    //Metodo que se ejecuta tras recibir la lista de unidades de cantidad
    private fun setQuantitiesUnit() {

        //Aplicamos el adapter al spinner
        binding.sQuantityUnit.adapter = addPantryPresenter.createAdapterQuantityUnit()
    }

    //Metodo que se ejecuta tras recibir la lista de lugares de almacenaje
    private fun setStorePlaces() {

        //Aplicamos el adapter al spinner
        binding.sStorePLace.adapter = addPantryPresenter.createAdapterStorePlace()
    }

    //Metodo para confirmar el cambio de pantalla de vuelta tras el escaneo del código de
    //barras
    private fun commit() {
        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, this)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    //Metodo que una vez seleccionada una fecha de vencimiento del dialogo del datapicker
    //la inserta en el campo de texto correspondiente.
    @SuppressLint("SetTextI18n")
    private fun showExpirationResult(year: Int, month: Int, day: Int) {
        binding.etExpirationDate.setText("$day/$month/$year")
    }

    //Metodo que una vez seleccionada una fecha de preferencia del dialogo del datapicker
    //la inserta en el campo de texto correspondiente.
    @SuppressLint("SetTextI18n")
    private fun showPreferenceResult(year: Int, month: Int, day: Int) {
        binding.etPreferenceDate.setText("$day/$month/$year")
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

    //Establece las traducciones de los textos
    override fun setTranslations() {
        binding.lBarcode.text = mutableTranslations[Constant.FIELD_BARCODE]!!
        binding.lProductName.text = mutableTranslations[Constant.FIELD_NAME]!!
        binding.lBrand.text = mutableTranslations[Constant.FIELD_BRAND]!!
        binding.lStorePlace.text = mutableTranslations[Constant.FIELD_PLACE]!!
        binding.lPrice.text = mutableTranslations[Constant.FIELD_PRICE]!!
        binding.lQuantity.text = mutableTranslations[Constant.FIELD_QUANTITY]!!
        binding.lQuantityUnit.text = mutableTranslations[Constant.FIELD_QUANTITY_UNIT]!!
        binding.lWeight.text = mutableTranslations[Constant.FIELD_WEIGHT]!!
        binding.lExpirationDate.text = mutableTranslations[Constant.FIELD_EXPIRATION_DATE]!!
        binding.lPreferenceDate.text = mutableTranslations[Constant.FIELD_PREFERENCE_DATE]!!
        binding.btnChangeImage.text = mutableTranslations[Constant.BTN_CHANGE_IMAGE]!!
        binding.btnScan.text = mutableTranslations[Constant.BTN_SCAN]!!
        if (mode == Constant.MODE_ADD || mode == Constant.MODE_SCAN) {
            binding.header.titleHeader.text = mutableTranslations[Constant.TITLE_ADD_PANTRY]!!
            binding.btnAddPurchaseProduct.text = mutableTranslations[Constant.BTN_ADD_PANTRY]!!

            //Una vez cargadas las traducciones hacemos el layout visible al usuario.
            binding.layoutAddPantry.visibility = View.VISIBLE
        } else {
            binding.header.titleHeader.text = mutableTranslations[Constant.TITLE_UPDATE_PANTRY]!!
            binding.btnAddPurchaseProduct.text = mutableTranslations[Constant.BTN_UPDATE_PANTRY]!!
        }
    }
}