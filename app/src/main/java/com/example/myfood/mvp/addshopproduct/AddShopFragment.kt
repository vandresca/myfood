package com.example.myfood.mvp.addshopproduct

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant.Companion.CONST_OK
import com.example.myfood.constants.Constant.Companion.MODE_ADD
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddShopFragmentBinding
import com.example.myfood.mvp.shoplist.ShopListFragment
import com.example.myfood.popup.Popup
import org.json.JSONObject


class AddShopFragment(private val mode: Int, private var idShop: String = "") : Fragment(),
    AddShopContract.View {

    private var _binding: AddShopFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addShopModel: AddShopModel
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = AddShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAddShop.visibility = View.INVISIBLE
        addShopModel = AddShopModel()
        addShopModel.getInstance(requireContext())
        addShopModel.getUserId(this) { userId -> onUserIdLoaded(userId) }
        addShopModel.getQuantitiesUnit(this) { quantities -> onQuantitiesLoaded(quantities) }
        addShopModel.getCurrentLanguage(this)
        { currentLanguage -> onCurrentLanguageLoaded(currentLanguage) }
        binding.btnASProduct.setOnClickListener { addUpdateShopToDB() }
    }

    override fun onLoadShopToUpdate(response: String?) {
        val json = JSONObject(response!!)
        if (json.get("response") == CONST_OK) {
            Handler(Looper.getMainLooper()).post {
                binding.etASName.setText(json.get("name").toString())
                binding.etASQuantity.text = SpannableStringBuilder(json.get("quantity").toString())
                binding.sASQuantityUnit.setSelection(
                    quantitiesUnitMutable.indexOf(
                        json.get("quantityUnit").toString()
                    )
                )
            }
        }
    }

    override fun onQuantitiesLoaded(quantitiesUnit: List<QuantityUnit>) {
        quantitiesUnitMutable = mutableListOf()
        quantitiesUnit.forEach {
            this.quantitiesUnitMutable.add(it.quantityUnit)
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
                binding.sASQuantityUnit.adapter = adapter
                binding.sASQuantityUnit.onItemSelectedListener =
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


    fun onUserIdLoaded(id: String) {
        userId = id
    }

    private fun addUpdateShopToDB() {
        val name = binding.etASName.text.toString()
        val quantity = binding.etASQuantity.text.toString()
        val quantityUnit = binding.sASQuantityUnit.selectedItem.toString()
        if (name.isNotEmpty()) {
            if (mode == MODE_ADD) {
                addShopModel.insertShop(this, name, quantity, quantityUnit, userId)
            } else {
                addShopModel.updateShop(this, name, quantity, quantityUnit, idShop)
            }
            loadFragment(ShopListFragment())
        } else {
            Popup.showInfo(requireContext(), resources, mutableTranslations["requiredName"]!!.text)
        }
    }

    override fun onInsertedShop(response: String?) {}
    override fun onUpdatedShop(response: String?) {}

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
        binding.layoutAddShop.visibility = View.VISIBLE
        binding.lASName.text = mutableTranslations["name"]!!.text
        binding.lASQuantity.text = mutableTranslations["quantity"]!!.text
        binding.lASQuantityUnit.text = mutableTranslations["quantityUnit"]!!.text
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text = mutableTranslations["addShopListTitle"]!!.text
            binding.btnASProduct.text = mutableTranslations["addShopListBtn"]!!.text
        } else {
            binding.header.titleHeader.text = mutableTranslations["updateShopListTitle"]!!.text
            binding.btnASProduct.text = mutableTranslations["updateShopListBtn"]!!.text
            addShopModel.getShopProduct(idShop) { data -> onLoadShopToUpdate(data) }
        }
    }

    override fun onCurrentLanguageLoaded(language: String) {
        addShopModel.getTranslations(this, language.toInt())
        { translations -> onTranslationsLoaded(translations) }
    }
}