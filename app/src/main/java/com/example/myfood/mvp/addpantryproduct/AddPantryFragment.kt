package com.example.myfood.mvp.addpantryproduct

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.databinding.AddPurchaseFragmentBinding
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.popup.Popup
import com.example.myfood.rest.OpenFoodREST
import com.example.myfood.util.DatePickerFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import org.json.JSONObject


class AddPantryFragment(private val mode: Int, private var idPantry: String = "") : Fragment(),
    AddPantryContract.View {

    private var _binding: AddPurchaseFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addPantryModel: AddPantryModel
    private lateinit var quantitiesUnit: List<String>
    private lateinit var places: List<String>

    companion object {
        const val MODE_SCAN = 2
        const val MODE_UPDATE = 1
        const val MODE_ADD = 0
        private const val CONST_OK = "OK"
        private const val CONST_DATAPICKER = "DataPicker"
        private const val CONST_SCAN = "SCAN"
        private val IMAGE_CHOOSE = 1000
        private val RESULT_OK = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddPurchaseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextAddButton()
        initDataPickers()
        initBtnScan()
        initBtnAddUpdate()
        initBtnChange()
        initDataSQLite()
    }

    private fun setTextAddButton() {
        if (mode == MODE_ADD || mode == MODE_SCAN) {
            binding.btnAddPurchaseProduct.text = "Add"
        } else {
            binding.btnAddPurchaseProduct.text = "Update"
        }
    }

    private fun initDataSQLite() {
        addPantryModel = AddPantryModel()
        this.context?.let { addPantryModel.getInstance(it) }
        addPantryModel.getUserId(this)
        addPantryModel.getQuantitiesUnit(this)
        addPantryModel.getPlaces(this)
        initDependingMode()
    }

    private fun initDependingMode() {
        if (mode == MODE_SCAN) initScanner()
        if (mode == MODE_ADD) binding.header.titleHeader.text = "Add Pantry"
        if (mode == MODE_UPDATE) {
            binding.header.titleHeader.text = "Update Pantry"
            addPantryModel.getPantryProduct(this, idPantry)
        }
    }

    override fun onLoadPantryToUpdate(response: String?) {
        val json = JSONObject(response)
        if (json.get("response") == CONST_OK) {
            Handler(Looper.getMainLooper()).post {
                val name = json.get("name").toString()
                if (!json.isNull("image")) {
                    Picasso.with(binding.ivProduct.context).load(json.get("image").toString())
                        .into(binding.ivProduct)
                }
                if (!json.get("expiredDate").toString().isEmpty())
                    binding.etExpirationDate.setText(json.get("expiredDate").toString())
                if (!json.get("preferenceDate").toString().isEmpty())
                    binding.etPreferenceDate.setText(json.get("preferenceDate").toString())
                binding.etProductName.setText(name)
                binding.etBarcode.setText(json.get("barcode").toString())
                binding.etQuantity.text = SpannableStringBuilder(json.get("quantity").toString())
                binding.sQuantityUnit.setSelection(
                    quantitiesUnit.indexOf(
                        json.get("quantityUnit").toString()
                    )
                )
                binding.sPLace.setSelection(places.indexOf(json.get("place").toString()))
                binding.etWeight.text = SpannableStringBuilder(json.get("weight").toString())
                binding.etPrice.text = SpannableStringBuilder(json.get("price").toString())
                binding.etBrand.setText(json.get("brand").toString())
            }

            Handler(Looper.getMainLooper()).post {

            }
        }

    }

    fun onUserIdLoaded(id: String) {
        userId = id
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_CHOOSE) {
            val uri = data?.data
            binding.ivProduct.tag = uri
            Handler(Looper.getMainLooper()).post {
                Picasso.with(requireContext()).load(uri).into(binding.ivProduct)
            }
        } else {
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
    }


    override fun fillProduct(barcode: String) {
        OpenFoodREST.openFoodRequest(this, barcode)
    }

    override fun onFillProductData(response: String?) {
        val json = JSONObject(response)
        if (json.get("status") == 1) {
            var nameProduct = ""
            var brand = ""
            var srcImage = ""
            val product = json.getJSONObject("product")
            if (product.has("generic_name")) {
                nameProduct = product.get("generic_name").toString()
                if (nameProduct.trim().isEmpty())
                    nameProduct = product.get("product_name").toString()
            } else {
                nameProduct = product.get("product_name").toString()
            }
            if (nameProduct.length > 28) {
                nameProduct = nameProduct.substring(0, 25) + "..."
            }
            if (product.has("brands")) {
                brand = product.get("brands").toString()
            }
            if (product.has("image_front_small_url")) {
                srcImage = product.get("image_front_small_url").toString()
            }

            binding.etProductName.setText(nameProduct)
            binding.etBrand.setText(brand)
            binding.ivProduct.tag = srcImage
            Handler(Looper.getMainLooper()).post {
                Picasso.with(requireContext()).load(srcImage).into(binding.ivProduct)
            }
        } else {
            Popup.showInfo(
                requireContext(),
                resources,
                "El producto no se encuentra en la base de datos"
            )
        }
    }

    private fun initDataPickers() {
        binding.dpExpirationDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showExpirationResult(year, month, day) }
            dialogDate.show(childFragmentManager, CONST_DATAPICKER)
        }
        binding.dpPreferenceDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showPreferenceResult(year, month, day) }
            dialogDate.show(childFragmentManager, CONST_DATAPICKER)
        }
    }

    private fun initBtnScan() {
        binding.btnScan.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt(CONST_SCAN)
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    private fun initBtnAddUpdate() {
        binding.btnAddPurchaseProduct.setOnClickListener { addUpdateProductToDB() }
    }

    private fun initBtnChange() {
        binding.btnChangeImage.setOnClickListener { chooseGallery() }
    }

    private fun chooseGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        //startActivity(i)
        startActivityForResult(i, IMAGE_CHOOSE)
    }

    private fun addUpdateProductToDB() {
        val barcode = binding.etBarcode.text.toString()
        val name = binding.etProductName.text.toString()
        val quantity = binding.etQuantity.text.toString()
        val quantityUnit = binding.sQuantityUnit.selectedItem.toString()
        val place = binding.sPLace.selectedItem.toString()
        val weight = binding.etWeight.text.toString()
        val price = binding.etPrice.text.toString()
        val expirationDate = binding.etExpirationDate.text.toString()
        val preferenceDate = binding.etPreferenceDate.text.toString()
        var image = ""
        if (binding.ivProduct.tag != null) image = binding.ivProduct.tag.toString()
        val brand = binding.etBrand.text.toString()

        if (name.isEmpty()) {
            Popup.showInfo(
                requireContext(),
                resources,
                "Debes introducir un nombre para añadir el producto"
            )
        } else if (expirationDate.isEmpty()) {
            Popup.showInfo(
                requireContext(),
                resources,
                "La fecha de vencimiento no puede estar vacía"
            )
        } else {
            if (mode != MODE_UPDATE) {
                addPantryModel.insertPantry(
                    this, barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, userId
                )
            } else {
                addPantryModel.updatePantry(
                    this, barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, idPantry
                )
            }
            loadFragment(PantryListFragment())
        }
    }

    override fun onInsertedProduct(response: String?) {}
    override fun onUpdatedProduct(response: String?) {}

    override fun onQuantitiesLoaded(quantitiesUnit: List<String>) {
        this.quantitiesUnit = quantitiesUnit
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                quantitiesUnit
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.sQuantityUnit.adapter = adapter
                binding.sQuantityUnit.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            //binding.etBarcode.setText(dropdownItems[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

    override fun onPlacesLoaded(places: List<String>) {
        this.places = places
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                places
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.sPLace.adapter = adapter
                binding.sPLace.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            //binding.etBarcode.setText(dropdownItems[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

    private fun commit() {
        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, this)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun showExpirationResult(year: Int, month: Int, day: Int) {
        binding.etExpirationDate.setText("$day/$month/$year")
    }

    private fun showPreferenceResult(year: Int, month: Int, day: Int) {
        binding.etPreferenceDate.setText("$day/$month/$year")
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}