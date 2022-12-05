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
    private lateinit var quantitiesUnit: List<String>

    companion object {
        const val MODE_UPDATE = 1
        const val MODE_ADD = 0
        private const val CONST_OK = "OK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.titleHeader.text = "Add Shop"
        addShopModel = AddShopModel()
        this.context?.let { addShopModel.getInstance(it) }
        addShopModel.getUserId(this)
        addShopModel.getQuantitiesUnit(this)
        binding.btnAddShopProduct.setOnClickListener { addUpdateShopToDB() }
        setMode()
    }


    private fun setMode() {
        if (mode == MODE_ADD) {
            binding.btnAddShopProduct.text = "Add"
        } else {
            binding.btnAddShopProduct.text = "Update"
            addShopModel.getShopProduct(this, idShop)
        }
    }

    override fun onLoadShopToUpdate(response: String?) {
        val json = JSONObject(response)
        if (json.get("response") == CONST_OK) {
            Handler(Looper.getMainLooper()).post {
                binding.etASName.setText(json.get("name").toString())
                binding.etASQuantity.text = SpannableStringBuilder(json.get("quantity").toString())
                binding.sSPQuantityUnit.setSelection(
                    quantitiesUnit.indexOf(
                        json.get("quantityUnit").toString()
                    )
                )
            }
        }
    }

    fun onQuantitiesLoaded(quantitiesUnit: List<String>) {
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
                binding.sSPQuantityUnit.adapter = adapter
                binding.sSPQuantityUnit.onItemSelectedListener =
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

    fun addUpdateShopToDB() {
        val name = binding.etASName.text.toString()
        val quantity = binding.etASQuantity.text.toString()
        val quantityUnit = binding.sSPQuantityUnit.selectedItem.toString()
        if (!name.isEmpty()) {
            if (mode == MODE_ADD) {
                addShopModel.insertShop(this, name, quantity, quantityUnit, userId)
            } else {
                addShopModel.updateShop(this, name, quantity, quantityUnit, idShop)
            }
            loadFragment(ShopListFragment())
        } else {
            Popup.showInfo(
                requireContext(),
                resources,
                "Debes introducir un nombre para añadir el producto"
            )
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
}