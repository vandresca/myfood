package com.example.myfood.mvp.addshopproduct

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.MODE_ADD
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddShopFragmentBinding
import com.example.myfood.mvp.shoplist.ShopListFragment
import com.example.myfood.mvvm.data.model.ShopProductEntity
import com.example.myfood.popup.Popup


class AddShopFragment(private val mode: Int, private var idShop: String = "") : Fragment(),
    AddShopContract.View {

    private var _binding: AddShopFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId: String
    private lateinit var addShopModel: AddShopModel
    private lateinit var addShopPresenter: AddShopPresenter
    private lateinit var quantitiesUnitMutable: MutableList<String>
    private var mutableTranslations: MutableMap<String, Translation>? = null

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
        addShopPresenter = AddShopPresenter(this, addShopModel, requireContext())
        userId = addShopPresenter.getUserId()
        setQuantities(addShopPresenter.getQuantitiesUnit())
        val currentLanguage = addShopPresenter.getCurrentLanguage()
        this.mutableTranslations = addShopPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        binding.btnASProduct.setOnClickListener { addUpdateShopToDB() }
    }

    override fun onLoadShopToUpdate(shopProductEntity: ShopProductEntity) {
        if (shopProductEntity.status == Constant.OK) {
            Handler(Looper.getMainLooper()).post {
                binding.etASName.setText(shopProductEntity.name)
                binding.etASQuantity.setText(shopProductEntity.quantity)
                binding.sASQuantityUnit.setSelection(
                    quantitiesUnitMutable.indexOf(shopProductEntity.quantityUnit)
                )
            }
        }
    }

    private fun setQuantities(quantitiesUnit: List<QuantityUnit>) {
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

    private fun addUpdateShopToDB() {
        val name = binding.etASName.text.toString()
        val quantity = binding.etASQuantity.text.toString()
        val quantityUnit = binding.sASQuantityUnit.selectedItem.toString()
        if (name.isNotEmpty()) {
            if (mode == MODE_ADD) {
                addShopModel.insertShop(name, quantity, quantityUnit, userId)
            } else {
                addShopModel.updateShop(name, quantity, quantityUnit, idShop)
            }
            loadFragment(ShopListFragment())
        } else {
            Popup.showInfo(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_NAME_REQUIRED)!!.text
            )
        }
    }

    override fun setTranslations() {
        binding.layoutAddShop.visibility = View.VISIBLE
        binding.lASName.text = mutableTranslations?.get(Constant.LABEL_NAME)!!.text
        binding.lASQuantity.text = mutableTranslations?.get(Constant.LABEL_QUANTITY)!!.text
        binding.lASQuantityUnit.text = mutableTranslations?.get(Constant.LABEL_QUANTITY_UNIT)!!.text
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_ADD_SHOPPING)!!.text
            binding.btnASProduct.text = mutableTranslations?.get(Constant.BTN_ADD_SHOPPING)!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_UPDATE_SHOPPING)!!.text
            binding.btnASProduct.text =
                mutableTranslations?.get(Constant.BTN_UPDATE_SHOPPING)!!.text
            addShopPresenter.getShopProduct(idShop).observe(this.viewLifecycleOwner)
            { data -> onLoadShopToUpdate(data) }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}