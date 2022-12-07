package com.example.myfood.mvp.pantrylist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.PantryListFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.optionaddpantry.OptionAddPantryFragment

class PantryListFragment : Fragment(), PantryListContract.View {
    private var _binding: PantryListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryListPresenter: PantryListPresenter
    private lateinit var pantryListModel: PantryListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PantryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun showUpdatePurchaseScreen(idPurchase: String) {
        loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPurchase))
    }

    private fun initialize() {
        binding.layoutPantryList.visibility = View.INVISIBLE
        pantryListModel = PantryListModel()
        pantryListModel.getInstance(requireContext())
        pantryListModel.getUserId(this) { userId -> onUserIdLoaded(userId) }
        pantryListModel.getCurrentLanguage(this)
        { currentLanguage -> onCurrentLanguageLoaded(currentLanguage) }
    }

    override fun onUserIdLoaded(idUser: String) {
        pantryListPresenter = PantryListPresenter(this, PantryListModel(), idUser)
        initAddPurchaseClick()
        initSearcher()
    }


    private fun initSearcher() {
        binding.etFilterPL.addTextChangedListener { watchText ->
            pantryListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(purchaseAdapter: PantryListAdapter) {
        binding.rvPL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPL.adapter = purchaseAdapter
        binding.tvPricePLProduct.text = String.format("%.2f", purchaseAdapter.getTotalPrice())
        binding.tvTotalPLProduct.visibility = View.VISIBLE
        binding.tvPricePLProduct.visibility = View.VISIBLE
        binding.tvCurrencyPLProduct.visibility = View.VISIBLE
    }

    private fun initAddPurchaseClick() {
        binding.addPLItem.setOnClickListener {
            loadFragment(OptionAddPantryFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
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


    @SuppressLint("SetTextI18n")
    private fun setTranslations() {
        binding.layoutPantryList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations[Constant.PANTRY_LIST_TITLE]!!.text
        binding.etFilterPL.hint = mutableTranslations[Constant.SEARCH]!!.text
        binding.tvTotalPLProduct.text = "${mutableTranslations[Constant.TOTAL]!!.text}:"
    }

    override fun onCurrentLanguageLoaded(language: String) {
        pantryListModel.getTranslations(this, language.toInt())
        { translations -> onTranslationsLoaded(translations) }
    }
}
