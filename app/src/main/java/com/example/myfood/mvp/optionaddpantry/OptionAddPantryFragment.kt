package com.example.myfood.mvp.optionaddpantry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.OptionAddPantryBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment


class OptionAddPantryFragment : Fragment(), OptionAddPantryContract.View {
    private var _binding: OptionAddPantryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mutableTranslations: MutableMap<String, Translation>
    private lateinit var optionAddPantryModel: OptionAddPantryModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = OptionAddPantryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutOptionAddPantry.visibility = View.INVISIBLE
        optionAddPantryModel = OptionAddPantryModel()
        optionAddPantryModel.getInstance(requireContext())
        optionAddPantryModel.getCurrentLanguage(this)
        binding.btnOptionBarCode.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_SCAN))
        }
        binding.btnOptionKeyboard.setOnClickListener {
            loadFragment(AddPantryFragment(Constant.MODE_ADD))
        }
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslations.put(it.word, it)
        }
        binding.layoutOptionAddPantry.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["addPantryTitle"]?.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        optionAddPantryModel.getTranslations(this, language.toInt())
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}