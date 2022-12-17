package com.example.myfood.mvp.addpantryproduct

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.IMAGE_CHOOSE
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


class AddPantryFragment(private val mode: Int, private var idPantry: String = "") : Fragment(),
    AddPantryContract.View {

    private var _binding: AddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addPantryModel: AddPantryModel
    private lateinit var addPantryPresenter: AddPantryPresenter
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private lateinit var placesMutable: MutableList<String>
    private var mutableTranslations: MutableMap<String, Translation>? = null

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
        addPantryPresenter = AddPantryPresenter(this, addPantryModel, requireContext())
        this.userId = addPantryPresenter.getUserId()
        setQuantities(addPantryPresenter.getQuantitiesUnit())
        setPlaces(addPantryModel.getPlaces())
        initDependingMode()
        val currentLanguage = addPantryPresenter.getCurrentLanguage()
        this.mutableTranslations = addPantryPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

    }

    private fun initDependingMode() {
        if (mode == Constant.MODE_SCAN) initScanner()
        if (mode == Constant.MODE_UPDATE) {
            addPantryPresenter.getPantryProduct(idPantry).observe(this.viewLifecycleOwner)
            { data -> onLoadPantryToUpdate(data) }
        }
    }

    override fun onLoadPantryToUpdate(result: PantryProductEntity) {
        if (result.status == Constant.OK) {
            Handler(Looper.getMainLooper()).post {
                if (result.image.isNotEmpty()) {
                    Picasso.with(binding.ivProduct.context)
                        .load(result.image)
                        .into(binding.ivProduct)
                }
                if (result.expiredDate.isNotEmpty())
                    binding.etExpirationDate.setText(result.expiredDate)
                if (result.preferenceDate.isNotEmpty())
                    binding.etPreferenceDate.setText(result.preferenceDate)
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
        }
        binding.layoutAddPantry.visibility = View.VISIBLE
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
        addPantryPresenter.getOpenFoodProduct("${Constant.OPEN_FOOD_URL}$barcode.json")
            .observe(this.viewLifecycleOwner)
            { result -> onFillProductData(result) }
    }

    override fun onFillProductData(result: OpenFoodEntity) {
        if (result.status == Constant.OK_INT) {
            var nameProduct = String()
            var brand = String()
            var srcImage = String()
            if (!result.product.genericName.isNullOrEmpty()) {
                nameProduct = result.product.genericName
            } else {
                if (!result.product.productName.isNullOrEmpty()) {
                    nameProduct = result.product.productName
                }
            }
            if (nameProduct.length > 28) {
                nameProduct = nameProduct.substring(0, 25) + "..."
            }
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
                Picasso.with(requireContext()).load(srcImage).into(binding.ivProduct)
            }
        } else {
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_PRODUCT_NOT_FOUND)!!.text
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
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_NOT_EMPTY_NAME)!!.text
            )
        } else if (expirationDate.isEmpty()) {
            Popup.showInfo(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_NOT_EMPTY_EXPIRED_DATE)!!.text
            )
        } else {
            if (mode != Constant.MODE_UPDATE) {
                addPantryPresenter.insertPantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, userId
                )
            } else {
                addPantryPresenter.updatePantry(
                    barcode, name, quantity, quantityUnit, place,
                    weight, price, expirationDate, preferenceDate, image, brand, idPantry
                )
            }
            loadFragment(PantryListFragment())
        }
    }

    private fun setQuantities(quantitiesUnit: List<QuantityUnit>?) {
        quantitiesUnitMutable = mutableListOf()
        quantitiesUnit!!.forEach {
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
    }

    private fun setPlaces(places: List<StorePlace>) {
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