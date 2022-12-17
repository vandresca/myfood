package com.example.myfood.mvp.pantryfeature

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.PantryNutrientFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.pantrylist.PantryListFragment
import com.example.myfood.mvvm.data.model.NutrientGroupEntity
import com.example.myfood.mvvm.data.model.NutrientListTypeEntity
import com.example.myfood.mvvm.data.model.PantryProductEntity
import com.example.myfood.popup.Popup
import com.squareup.picasso.Picasso


class PantryNutrientFragment(
    private val pantryProduct: PantryProductEntity,
) : Fragment(),
    PantryNutrientContract.View {

    private var _binding: PantryNutrientFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var nutrientsGroup: List<String>
    private lateinit var pantryNutrientModel: PantryNutrientModel
    private lateinit var pantryNutrientPresenter: PantryNutrientPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PantryNutrientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutPantryNutrient.visibility = View.INVISIBLE
        pantryNutrientModel = PantryNutrientModel()
        pantryNutrientPresenter =
            PantryNutrientPresenter(this, pantryNutrientModel, requireContext())
        val currentLanguage = pantryNutrientPresenter.getCurrentLanguage()
        this.mutableTranslations = pantryNutrientPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        setImage()
        pantryNutrientPresenter.getNutrients().observe(this.viewLifecycleOwner)
        { groupNutrients -> onNutrientsLoaded(groupNutrients) }
        pantryNutrientPresenter.getNutrientsByType(Constant.LABEL_GENERAL, pantryProduct.idFood)
            .observe(this.viewLifecycleOwner)
            { nutrients -> onNutrientsTypeLoaded(nutrients) }
        binding.btnDeletePantryProduct.setOnClickListener {
            Popup.showConfirm(
                requireContext(), resources,
                mutableTranslations?.get(Constant.MSG_DELETE_PANTRY_QUESTION)!!.text,
                mutableTranslations?.get(Constant.BTN_YES)!!.text,
                mutableTranslations?.get(Constant.BTN_NO)!!.text
            ) {
                pantryNutrientPresenter.deletePantry(pantryProduct.idPantry)
                loadFragment(PantryListFragment())
            }
        }
        binding.btnUpdatePantryProduct.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_UPDATE, pantryProduct.idPantry))
        }
        binding.btnCharacteristics.setOnClickListener {
            loadFragment(PantryFeatureFragment(pantryProduct.idPantry))
        }

    }

    fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onNutrientsLoaded(nutrientGEntity: NutrientGroupEntity) {
        if (nutrientGEntity.status == Constant.OK) {
            nutrientsGroup = nutrientGEntity.nutrients
            this.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    nutrientsGroup
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    binding.sGroupNutrients.adapter = adapter
                    binding.sGroupNutrients.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                selectGroupNutrient(position)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                }
            }
        }
    }

    private fun selectGroupNutrient(position: Int) {
        pantryNutrientPresenter.getNutrientsByType(
            nutrientsGroup[position], pantryProduct.idFood
        ).observe(this.viewLifecycleOwner)
        { nutrients -> onNutrientsTypeLoaded(nutrients) }
    }


    @SuppressLint("SetTextI18n")
    override fun onNutrientsTypeLoaded(nutrientGTEntity: NutrientListTypeEntity) {
        val layoutContentNutrient = binding.layoutContentNutrients
        layoutContentNutrient.removeAllViews()
        if (nutrientGTEntity.status == Constant.OK) {
            nutrientGTEntity.foodNutrients.forEach {
                val textView = TextView(requireContext())
                val layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                layoutParams.setMargins(60, 40, 0, 0)
                textView.setTextColor(Color.parseColor(Constant.COLOR_TURQUOISE))
                val text = SpannableString("${it.column}: ${it.value}")
                val boldSpan = StyleSpan(Typeface.BOLD)
                text.setSpan(boldSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textView.text = text
                layoutContentNutrient.addView(textView, layoutParams)
            }
        }
    }

    override fun setTranslations() {
        binding.header.titleHeader.text = pantryProduct.name
        binding.btnCharacteristics.text =
            mutableTranslations?.get(Constant.BTN_CHARACTERISTICS)!!.text
        binding.layoutPantryNutrient.visibility = View.VISIBLE
    }

    private fun setImage() {
        if (pantryProduct.image.isNotEmpty()) {
            Picasso.with(binding.ivProduct.context)
                .load(pantryProduct.image)
                .into(binding.ivProduct)
        }
    }
}