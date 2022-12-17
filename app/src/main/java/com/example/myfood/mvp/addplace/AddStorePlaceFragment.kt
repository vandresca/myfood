package com.example.myfood.mvp.addplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.MODE_ADD
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.AddStorePlaceFragmentBinding
import com.example.myfood.mvp.storeplace.StorePlaceListFragment
import com.example.myfood.popup.Popup


class AddStorePlaceFragment(
    private val mode: Int,
    private val storePlaceToUpdate: StorePlace? = null
) : Fragment(),
    AddStorePlaceContract.View {

    private var _binding: AddStorePlaceFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addStorePlaceModel: AddStorePlaceModel
    private lateinit var addStorePlacePresenter: AddStorePlacePresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = AddStorePlaceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAddPlace.visibility = View.INVISIBLE
        addStorePlaceModel = AddStorePlaceModel()
        addStorePlacePresenter = AddStorePlacePresenter(this, addStorePlaceModel, requireContext())
        val currentLanguage = addStorePlacePresenter.getCurrentLanguage()
        this.mutableTranslations = addStorePlacePresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        if (mode == Constant.MODE_UPDATE) binding.etAddPlaceName.setText(storePlaceToUpdate!!.storePlace)

    }

    private fun initAddStorePlaceBtn() {
        binding.btnAddPlaceProduct.setOnClickListener {
            val quantityUnit = binding.etAddPlaceName.text.toString()
            if (quantityUnit.isNotEmpty()) {
                if (mode == MODE_ADD) {
                    addStorePlacePresenter.addStorePlace(quantityUnit)
                } else {
                    addStorePlacePresenter.updateStorePlace(
                        binding.etAddPlaceName.text.toString(),
                        storePlaceToUpdate!!.idStorePlace.toString()
                    )
                }
                loadFragment(StorePlaceListFragment())
            } else {
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations?.get(Constant.MSG_STORE_PLACE_REQUIRED)!!.text
                )
            }
        }
    }

    override fun setTranslations() {
        binding.layoutAddPlace.visibility = View.VISIBLE
        binding.lAPlaceName.text = mutableTranslations?.get(Constant.LABEL_STORE_PLACE)!!.text
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_ADD_STORE_PLACE)!!.text
            binding.btnAddPlaceProduct.text =
                mutableTranslations?.get(Constant.BTN_ADD_STORE_PLACE)!!.text
        } else {
            binding.header.titleHeader.text =
                mutableTranslations?.get(Constant.TITLE_UPDATE_STORE_PLACE)!!.text
            binding.btnAddPlaceProduct.text =
                mutableTranslations?.get(Constant.BTN_UPDATE_STORE_PLACE)!!.text
        }
        initAddStorePlaceBtn()
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}