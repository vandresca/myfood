package com.example.myfood.mvp.addpantryproduct

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.StorePlace
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddPantryFragmentBinding
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvvm.data.model.OpenFoodEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.popup.Popup
import com.example.myfood.util.DatePickerFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.*


class AddPantryFragment(private val mode: Int, private var idPantry: String = "") : Fragment(),
    AddPantryContract.View {

    //Declaración de variables globales
    private var _binding: AddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addPantryModel: AddPantryModel
    private lateinit var addPantryPresenter: AddPantryPresenter
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private lateinit var placesMutable: MutableList<String>
    private var mutableTranslations: MutableMap<String, Translation>? = null

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

        //Incializamos los datpickers, elementos para seleccionar una fecha en el calendario
        initDataPickers()

        //Inicializamos los botones
        initBtnScan()
        initBtnAddUpdate()
        initBtnChange()

        //Creamos el modelo
        addPantryModel = AddPantryModel()

        //Creamos el presentador
        addPantryPresenter = AddPantryPresenter(this, addPantryModel, requireContext())

        //Obtenemos el usuario de la App
        this.userId = addPantryPresenter.getUserId()

        //Inicializamos los combo de unidades de cantidad y lugares de almacenaje
        setQuantities(addPantryPresenter.getQuantitiesUnit())
        setPlaces(addPantryModel.getPlaces())

        //Aplicamos acciones segun el modo del que venimos
        initDependingMode()

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = addPantryPresenter.getCurrentLanguage()
        this.mutableTranslations = addPantryPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
    }

    private fun initDependingMode() {

        //Si venimos del modo SCAN iniciamos el escaner de código de barras
        //Si venimos de modificar (UPDATE) cargamos los datos del producto despensa
        if (mode == Constant.MODE_SCAN) initScanner()
        if (mode == Constant.MODE_UPDATE) {
            addPantryPresenter.getPantryProduct(idPantry).observe(this.viewLifecycleOwner)
            { data -> onLoadPantryToUpdate(data) }
        }
    }

    // Carga el producto despensa en caso de venir en modo UPDATE en pantalla
    override fun onLoadPantryToUpdate(result: PantryProductEntity) {

        //Verificamos que la respuesta es correcta
        if (result.status == Constant.OK) {

            //Verificamos si la imagen no está vacia y si es así la cargamos
            if (result.image.isNotEmpty()) {
                val decoded = Base64.getDecoder().decode(result.image)
                val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
                binding.ivProduct.setImageBitmap(bitmap)
            }

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
                quantitiesUnitMutable.indexOf(result.quantityUnit)
            )
            binding.sPLace.setSelection(
                placesMutable.indexOf(result.storePlace)
            )
            binding.etWeight.setText(result.weight)
            binding.etPrice.setText(result.price)
            binding.etBrand.setText(result.brand)
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
                fillProduct(result.contents)
            }
        } else {
            binding.etBarcode.setText("")
        }
        commit()
    }


    override fun fillProduct(barcode: String) {
        //Llamamos al metodo getOpenFoodProduct para devolver los atributos del producto
        //alimenticio a partir del código de barras.
        addPantryPresenter.getOpenFoodProduct("${Constant.OPEN_FOOD_URL}$barcode.json")
            .observe(this.viewLifecycleOwner)
            { result -> onFillProductData(result) }
    }

    //Se ejecuta tras la llamada de getOpenFoodProduct
    override fun onFillProductData(result: OpenFoodEntity) {

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

            binding.ivProduct.tag = srcImage
            Handler(Looper.getMainLooper()).post {
                Picasso.get().load(srcImage).into(binding.ivProduct)
            }
        } else {
            //En caso de que la respuesta no sea correcta mostramos
            //un mensaje al usuario indicando que el producto no ha sido
            //hallado.
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_PRODUCT_NOT_FOUND)!!.text
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

    private fun initBtnScan() {
        //Inicialiamos el boton de escaneo para que inicie el escaner de
        //código de barras cuando se haga click
        binding.btnScan.setOnClickListener { initScanner() }
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

    private fun initBtnAddUpdate() {

        //Inicializamos el boton de Añadir Producto Despensa para que inicie el método de
        //inserción al hacer click.
        binding.btnAddPurchaseProduct.setOnClickListener { addUpdateProductToDB() }
    }

    private fun initBtnChange() {
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
        val place = binding.sPLace.selectedItem.toString()
        val weight = binding.etWeight.text.toString()
        val price = binding.etPrice.text.toString()
        val expirationDate = binding.etExpirationDate.text.toString()
        val preferenceDate = binding.etPreferenceDate.text.toString()
        val brand = binding.etBrand.text.toString()

        //Obtenemos el bitmap del ImageView
        val bitmap = binding.ivProduct.drawable.toBitmap()

        //Creamos un outputStream de tipo ByteArray
        val stream = ByteArrayOutputStream()

        //Llenamos el outputstream con el bitmap con calidad 100%
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        //Transformamos el outputstream a bytearray
        val byteArray = stream.toByteArray()

        //Transformamos el byteArray a String en codificación base64
        val image = Base64.getEncoder().encodeToString(byteArray)

        //Verificamos que hayamos insertado un mombre de producto y si no mostramos
        //un  mensaje al usuario indicando que lo haga
        if (name.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_NOT_EMPTY_NAME)!!.text
            )
            //Verificamos que hayamos insertado una fecha de caducidad y si  no mostramos
            //un mensaje al usuario indicando que lo haga
        } else if (expirationDate.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_NOT_EMPTY_EXPIRED_DATE)!!.text
            )
        } else {
            //Si todo es correcto y estamos en el modo Actualizar, actualizamos
            //el producto con los datos y cuando recibimos respuesta correcta
            // volvemos a pantalla Despensa
            if (mode != Constant.MODE_UPDATE) {
                addPantryPresenter.insertPantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, userId
                ).observe(this.viewLifecycleOwner) { result ->
                    if (result.status == Constant.OK) {
                        loadFragment(PantryListFragment())
                    }
                }
                //Si todo es correcto y estamos en el modo insertar, insertamos el nuevo
                //producto con los datos y cuando recibimos respuesta correcta volvemos
                //a la pantalla Despensa
            } else {
                addPantryPresenter.updatePantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, idPantry
                ).observe(this.viewLifecycleOwner) { result ->
                    if (result.status == Constant.OK) {
                        loadFragment(PantryListFragment())
                    }
                }
            }
        }
    }

    //Metodo que se ejecuta tras recibir la lista de unidades de cantidad
    private fun setQuantities(quantitiesUnit: List<QuantityUnit>?) {

        //Declaramos una variable global de tipo lista mutable
        //de tipo String.
        //Recorremos los objetos de unidades de cantidad e insertamos en la lista
        //las unidades de cantidad como String
        quantitiesUnitMutable = mutableListOf()
        quantitiesUnit!!.forEach {
            quantitiesUnitMutable.add(it.quantityUnit)
        }

        //Poblamos el combo con la lista
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            quantitiesUnitMutable
        ).also { adapter ->
            // Especifica el layout para usar cuando la lista de opciones aparece
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Aplicamos el adapter al spinner
            binding.sQuantityUnit.adapter = adapter
        }
    }

    //Metodo que se ejecuta tras recibir la lista de lugares de almacenaje
    private fun setPlaces(places: List<StorePlace>) {

        //Declaramos una variable global de tipo lista mutable
        //de tipo String.
        //Recorremos los objetos de lugares de almacenaje e insertamos en la lista
        //los lugares de almacenaje como String
        placesMutable = mutableListOf()
        places.forEach {
            placesMutable.add(it.storePlace)
        }
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            placesMutable
        ).also { adapter ->
            // Especifica el layout para usar cuando la lista de opciones aparece
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Aplicamos el adapter al spinner
            binding.sPLace.adapter = adapter
        }
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
        binding.layoutAddPantry.visibility = View.VISIBLE
        binding.lBarcode.text = mutableTranslations?.get(Constant.FIELD_BARCODE)!!.text
        binding.lProductName.text = mutableTranslations?.get(Constant.FIELD_NAME)!!.text
        binding.lBrand.text = mutableTranslations?.get(Constant.FIELD_BRAND)!!.text
        binding.lPlace.text = mutableTranslations?.get(Constant.FIELD_PLACE)!!.text
        binding.lPrice.text = mutableTranslations?.get(Constant.FIELD_PRICE)!!.text
        binding.lQuantity.text = mutableTranslations?.get(Constant.FIELD_QUANTITY)!!.text
        binding.lQuantityUnit.text = mutableTranslations?.get(Constant.FIELD_QUANTITY_UNIT)!!.text
        binding.lWeight.text = mutableTranslations?.get(Constant.FIELD_WEIGHT)!!.text
        binding.lExpirationDate.text =
            mutableTranslations?.get(Constant.FIELD_EXPIRATION_DATE)!!.text
        binding.lPreferenceDate.text =
            mutableTranslations?.get(Constant.FIELD_PREFERENCE_DATE)!!.text
        binding.btnChangeImage.text = mutableTranslations?.get(Constant.BTN_CHANGE_IMAGE)!!.text
        binding.btnScan.text = mutableTranslations?.get(Constant.BTN_SCAN)!!.text
        if (mode == Constant.MODE_ADD || mode == Constant.MODE_SCAN) {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_ADD_PANTRY)!!.text
            binding.btnAddPurchaseProduct.text =
                mutableTranslations?.get(Constant.BTN_ADD_PANTRY)!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_UPDATE_PANTRY)!!.text
            binding.btnAddPurchaseProduct.text =
                mutableTranslations?.get(Constant.BTN_UPDATE_PANTRY)!!.text
        }
    }
}