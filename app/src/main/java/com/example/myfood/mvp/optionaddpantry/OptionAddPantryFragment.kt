package com.example.myfood.mvp.optionaddpantry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.OptionAddPantryFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment


class OptionAddPantryFragment : Fragment(), OptionAddPantryContract.View {
    private var _binding: OptionAddPantryFragmentBinding? = null
    private val binding get() = _binding!!
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var optionAddPantryModel: OptionAddPantryModel
    private lateinit var optionAddPantryPresenter: OptionAddPantryPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = OptionAddPantryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutOptionAddPantry.visibility = View.INVISIBLE
        optionAddPantryModel = OptionAddPantryModel()
        optionAddPantryPresenter = OptionAddPantryPresenter(
            this,
            optionAddPantryModel, requireContext()
        )
        val currentLanguage = optionAddPantryPresenter.getCurrentLanguage()
        this.mutableTranslations = optionAddPantryPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        binding.btnOptionBarCode.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_SCAN))
        }
        binding.btnOptionKeyboard.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_ADD))
        }
    }

    override fun setTranslations() {
        binding.layoutOptionAddPantry.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations?.get(Constant.TITLE_ADD_PANTRY)?.text
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}