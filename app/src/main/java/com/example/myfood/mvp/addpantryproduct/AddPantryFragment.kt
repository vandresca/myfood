package com.example.myfood.mvp.addpantryproduct

import android.annotation.SuppressLint
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
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.IMAGE_CHOOSE
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddPantryFragmentBinding
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.popup.Popup
import com.example.myfood.rest.OpenFoodREST
import com.example.myfood.util.DatePickerFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import org.json.JSONObject


class AddPantryFragment(private val mode: Int, private var idPantry: String = "") : Fragment(),
    AddPantryContract.View {

    private var _binding: AddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addPantryModel: AddPantryModel
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private lateinit var placesMutable: MutableList<String>
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddPantryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAddPantry.visibility = View.INVISIBLE
        initDataPickers()
        initBtnScan()
        initBtnAddUpdate()
        initBtnChange()
        initDataSQLite()
    }

    private fun initDataSQLite() {
        addPantryModel = AddPantryModel()
        addPantryModel.getInstance(requireContext())
        addPantryModel.getUserId(this) { userId -> onUserIdLoaded(userId) }
        addPantryModel.getQuantitiesUnit(this) { quantities -> onQuantitiesLoaded(quantities) }
        addPantryModel.getCurrentLanguage(this)
        { currentLanguage -> onCurrentLanguageLoaded(currentLanguage) }

    }

    private fun initDependingMode() {
        if (mode == Constant.MODE_SCAN) initScanner()
        if (mode == Constant.MODE_UPDATE) {
            addPantryModel.getPantryProduct(idPantry) { data -> onLoadPantryToUpdate(data) }
        }
    }

    override fun onLoadPantryToUpdate(response: String?) {
        val json = JSONObject(response!!)
        if (json.get(Constant.JSON_RESPONSE) == Constant.CONST_OK) {
            Handler(Looper.getMainLooper()).post {
                val name = json.get(Constant.JSON_NAME).toString()
                if (!json.isNull(Constant.JSON_IMAGE) && json.get(Constant.JSON_IMAGE).toString()
                        .isNotEmpty()
                ) {
                    Picasso.with(binding.ivProduct.context)
                        .load(json.get(Constant.JSON_IMAGE).toString())
                        .into(binding.ivProduct)
                }
                if (json.get(Constant.JSON_EXPIRED_DATE).toString().isNotEmpty())
                    binding.etExpirationDate.setText(
                        json.get(Constant.JSON_EXPIRED_DATE).toString()
                    )
                if (json.get(Constant.JSON_PREFERENCE_DATE).toString().isNotEmpty())
                    binding.etPreferenceDate.setText(
                        json.get(Constant.JSON_PREFERENCE_DATE).toString()
                    )
                binding.etProductName.setText(name)
                binding.etBarcode.setText(json.get(Constant.JSON_BARCODE).toString())
                binding.etQuantity.text =
                    SpannableStringBuilder(json.get(Constant.JSON_QUANTITY).toString())
                binding.sQuantityUnit.setSelection(
                    quantitiesUnitMutable.indexOf(
                        json.get(Constant.JSON_QUANTITY_UNIT).toString()
                    )
                )
                binding.sPLace.setSelection(
                    placesMutable.indexOf(
                        json.get(Constant.JSON_PLACE).toString()
                    )
                )
                binding.etWeight.text =
                    SpannableStringBuilder(json.get(Constant.JSON_WEIGHT).toString())
                binding.etPrice.text =
                    SpannableStringBuilder(json.get(Constant.JSON_PRICE).toString())
                binding.etBrand.setText(json.get(Constant.JSON_BRAND).toString())
            }

            Handler(Looper.getMainLooper()).post {

            }
        }

    }

    fun onUserIdLoaded(id: String) {
        userId = id
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Constant.RESULT_GALLERY_OK && requestCode == IMAGE_CHOOSE) {
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
        val json = JSONObject(response!!)
        if (json.get(Constant.JSON_STATUS) == 1) {
            var nameProduct: String
            var brand = String()
            var srcImage = String()
            val product = json.getJSONObject(Constant.JSON_PRODUCT)
            if (product.has(Constant.JSON_GENERIC_NAME)) {
                nameProduct = product.get(Constant.JSON_GENERIC_NAME).toString()
                if (nameProduct.trim().isEmpty())
                    nameProduct = product.get(Constant.JSON_PRODUCT_NAME).toString()
            } else {
                nameProduct = product.get(Constant.JSON_PRODUCT_NAME).toString()
            }
            if (nameProduct.length > 28) {
                nameProduct = nameProduct.substring(0, 25) + "..."
            }
            if (product.has(Constant.JSON_BRANDS)) {
                brand = product.get(Constant.JSON_BRANDS).toString()
            }
            if (product.has(Constant.JSON_IMAGE_FRONT_SMALL_URL)) {
                srcImage = product.get(Constant.JSON_IMAGE_FRONT_SMALL_URL).toString()
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
                mutableTranslations[Constant.PRODUCT_NOT_FOUND]!!.text
            )
        }
    }

    private fun initDataPickers() {
        binding.dpExpirationDate.setOnClickListener {
            val dialogDate =
                DatePickerFragment { year, month, day -> showExpirationResult(year, month, day) }
            dialogDate.show(childFragmentManager, Constant.CONST_DPICKER)
        }
        binding.dpPreferenceDate.setOnClickListener {
            val dialogDate =
                DatePickerFragment { year, month, day -> showPreferenceResult(year, month, day) }
            dialogDate.show(childFragmentManager, Constant.CONST_DPICKER)
        }
    }

    private fun initBtnScan() {
        binding.btnScan.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt(Constant.CONST_SCAN)
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
        startActivityForResult(i, Constant.IMAGE_CHOOSE)
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
                requireContext(), resources,
                mutableTranslations[Constant.NOT_EMPTY_NAME]!!.text
            )
        } else if (expirationDate.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations[Constant.NOT_EMPTY_EXPIRED_DATE]!!.text
            )
        } else {
            if (mode != Constant.MODE_UPDATE) {
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

    override fun onQuantitiesLoaded(quantitiesUnit: List<QuantityUnit>) {
        quantitiesUnitMutable = mutableListOf()
        quantitiesUnit.forEach {
            quantitiesUnitMutable.add(it.quantityUnit)
        }
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                quantitiesUnitMutable
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
        addPantryModel.getPlaces(this) { places -> onPlacesLoaded(places) }
    }

    override fun onPlacesLoaded(places: List<StorePlace>) {
        placesMutable = mutableListOf()
        places.forEach {
            placesMutable.add(it.storePlace)
        }
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                placesMutable
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
        initDependingMode()
    }

    private fun commit() {
        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, this)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun showExpirationResult(year: Int, month: Int, day: Int) {
        binding.etExpirationDate.setText("$day/$month/$year")
    }

    @SuppressLint("SetTextI18n")
    private fun showPreferenceResult(year: Int, month: Int, day: Int) {
        binding.etPreferenceDate.setText("$day/$month/$year")
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf()
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        setTranslations()
    }

    private fun setTranslations() {
        binding.layoutAddPantry.visibility = View.VISIBLE
        binding.lBarcode.text = mutableTranslations[Constant.BARCODE]!!.text
        binding.lProductName.text = mutableTranslations[Constant.NAME]!!.text
        binding.lBrand.text = mutableTranslations[Constant.BRAND]!!.text
        binding.lPlace.text = mutableTranslations[Constant.PLACE]!!.text
        binding.lPrice.text = mutableTranslations[Constant.PRICE]!!.text
        binding.lQuantity.text = mutableTranslations[Constant.QUANTITY]!!.text
        binding.lQuantityUnit.text = mutableTranslations[Constant.QUANTITY_UNIT]!!.text
        binding.lWeight.text = mutableTranslations[Constant.WEIGHT]!!.text
        binding.lExpirationDate.text = mutableTranslations[Constant.EXPIRATION_DATE]!!.text
        binding.lPreferenceDate.text = mutableTranslations[Constant.PREFERENCE_DATE]!!.text
        binding.btnChangeImage.text = mutableTranslations[Constant.BTN_CHANGE_IMAGE]!!.text
        binding.btnScan.text = mutableTranslations[Constant.BTN_SCAN]!!.text
        if (mode == Constant.MODE_ADD || mode == Constant.MODE_SCAN) {
            binding.header.titleHeader.text = mutableTranslations[Constant.ADD_PANTRY_TITLE]!!.text
            binding.btnAddPurchaseProduct.text = mutableTranslations[Constant.BTN_ADD_PANTRY]!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations[Constant.UPDATE_PANTRY_TITLE]!!.text
            binding.btnAddPurchaseProduct.text =
                mutableTranslations[Constant.BTN_UPDATE_PANTRY]!!.text
        }
    }

    override fun onCurrentLanguageLoaded(language: String) {
        addPantryModel.getTranslations(this, language.toInt())
        { translations -> onTranslationsLoaded(translations) }
    }

}