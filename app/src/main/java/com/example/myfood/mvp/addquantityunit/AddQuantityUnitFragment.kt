package com.example.myfood.mvp.addquantityunit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.MODE_ADD
import com.example.myfood.constants.Constant.Companion.MODE_UPDATE
import com.example.myfood.databases.databasesqlite.entity.QuantityUnit
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddQuantityUnitFragmentBinding
import com.example.myfood.mvp.quantityunit.QuantityUnitListFragment
import com.example.myfood.popup.Popup


class AddQuantityUnitFragment(
    private val mode: Int,
    private val quantityUnitToUpdate: QuantityUnit? = null
) : Fragment(),
    AddQuantityUnitContract.View {

    private var _binding: AddQuantityUnitFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addQuantityUnitModel: AddQuantityUnitModel
    private lateinit var addQuantityUnitPresenter: AddQuantityUnitPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = AddQuantityUnitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAddQuantityUnit.visibility = View.INVISIBLE
        addQuantityUnitModel = AddQuantityUnitModel()
        addQuantityUnitPresenter = AddQuantityUnitPresenter(
            this,
            addQuantityUnitModel, requireContext()
        )
        val currentLanguage = addQuantityUnitPresenter.getCurrentLanguage()
        this.mutableTranslations = addQuantityUnitPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        if (mode == MODE_UPDATE) binding.etAddQuantityUnitName.setText(quantityUnitToUpdate!!.quantityUnit)
    }

    private fun initAddQuantityUnitBtn() {
        binding.btnAddQuantityUnit.setOnClickListener {
            val quantityUnit = binding.etAddQuantityUnitName.text.toString()
            if (quantityUnit.isNotEmpty()) {
                if (mode == MODE_ADD) {
                    addQuantityUnitPresenter.addQuantityUnit(quantityUnit)
                } else {
                    addQuantityUnitPresenter.updateQuantityUnit(
                        quantityUnit,
                        quantityUnitToUpdate!!.idQuantityUnit.toString()
                    )
                }
                loadFragment(QuantityUnitListFragment())
            } else {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_QUANTIY_UNIT_REQUIRED)!!.text
                )
            }
        }
    }

    override fun setTranslations() {
        binding.layoutAddQuantityUnit.visibility = View.VISIBLE
        binding.lAddQuantityUnitName.text =
            mutableTranslations?.get(Constant.LABEL_QUANTITY_UNIT)!!.text
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_ADD_QUANTITY_UNIT)!!.text
            binding.btnAddQuantityUnit.text =
                mutableTranslations?.get(Constant.BTN_ADD_QUANTITY_UNIT)!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_UPDATE_QUANTITY_UNIT)!!.text
            binding.btnAddQuantityUnit.text =
                mutableTranslations?.get(Constant.BTN_UPDATE_QUANTITY_UNIT)!!.text
        }
        initAddQuantityUnitBtn()
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}