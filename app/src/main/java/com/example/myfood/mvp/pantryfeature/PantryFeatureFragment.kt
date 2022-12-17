package com.example.myfood.mvp.pantryfeature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.PantryFeatureFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.popup.Popup
import com.squareup.picasso.Picasso


class PantryFeatureFragment(private var idPantry: String) : Fragment(),
    PantryFeatureContract.View {

    private var _binding: PantryFeatureFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryProduct: PantryProductEntity
    private lateinit var pantryFeatureModel: PantryFeatureModel
    private lateinit var pantryFeaturePresenter: PantryFeaturePresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PantryFeatureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutPantryFeature.visibility = View.INVISIBLE
        pantryFeatureModel = PantryFeatureModel()
        pantryFeaturePresenter = PantryFeaturePresenter(this, pantryFeatureModel, requireContext())
        val currentLanguage = pantryFeaturePresenter.getCurrentLanguage()
        this.mutableTranslations = pantryFeaturePresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        pantryFeaturePresenter.getPantryProduct(idPantry).observe(this.viewLifecycleOwner)
        { product -> onLoadPantryFeature(product) }

        binding.btnDeletePantryProduct.setOnClickListener {
            Popup.showConfirm(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_DELETE_PANTRY_QUESTION)!!.text,
                mutableTranslations?.get(Constant.BTN_YES)!!.text,
                mutableTranslations?.get(Constant.BTN_NO)!!.text
            ) {
                pantryFeaturePresenter.deletePantry(idPantry)
                loadFragment(PantryListFragment())
            }
        }
        binding.btnUpdatePantryProduct.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPantry))
        }
        binding.btnNutrients.setOnClickListener {
            loadFragment(PantryNutrientFragment(pantryProduct))
        }

    }


    override fun onLoadPantryFeature(pantryProductEntity: PantryProductEntity) {
        if (pantryProductEntity.status == Constant.OK) {
            pantryProduct = pantryProductEntity
            Handler(Looper.getMainLooper()).post {
                if (pantryProduct.image.isNotEmpty()) {
                    Picasso.with(binding.ivProduct.context)
                        .load(pantryProduct.image)
                        .into(binding.ivProduct)
                }
                binding.header.titleHeader.text = pantryProduct.name
                binding.leExpirationDate.text = pantryProduct.expiredDate
                binding.lePreferenceDate.text = pantryProduct.preferenceDate
                binding.leBarcode.text = pantryProduct.barcode
                binding.leQuantity.text = pantryProduct.quantity
                binding.leQuantityUnit.text = pantryProduct.quantityUnit
                binding.lePlace.text = pantryProduct.storePlace
                binding.leWeight.text = pantryProduct.weight
                binding.lePrice.text = pantryProduct.price
                binding.leBrand.text = pantryProduct.brand
                binding.layoutPantryFeature.visibility = View.VISIBLE
            }
        }

    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun setTranslations() {
        binding.lBarcode.text = mutableTranslations?.get(Constant.LABEL_BARCODE)!!.text
        binding.lBrand.text = mutableTranslations?.get(Constant.LABEL_BRAND)!!.text
        binding.lPlace.text = mutableTranslations?.get(Constant.LABEL_PLACE)!!.text
        binding.lPrice.text = mutableTranslations?.get(Constant.LABEL_PRICE)!!.text
        binding.lQuantity.text = mutableTranslations?.get(Constant.LABEL_QUANTITY)!!.text
        binding.lQuantityUnit.text = mutableTranslations?.get(Constant.LABEL_QUANTITY_UNIT)!!.text
        binding.lWeight.text = mutableTranslations?.get(Constant.LABEL_WEIGHT)!!.text
        binding.lExpirationDate.text =
            mutableTranslations?.get(Constant.LABEL_EXPIRATION_DATE)!!.text
        binding.lPreferenceDate.text =
            mutableTranslations?.get(Constant.LABEL_PREFERENCE_DATE)!!.text
        binding.btnNutrients.text = mutableTranslations?.get(Constant.BTN_NUTRIENTS)!!.text
    }
}