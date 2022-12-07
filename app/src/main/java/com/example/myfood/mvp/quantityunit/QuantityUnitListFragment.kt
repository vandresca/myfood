package com.example.myfood.mvp.quantityunit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.QuantityUnitListFragmentBinding

class QuantityUnitListFragment : Fragment(), QuantityUnitListContract.View {
    private var _binding: QuantityUnitListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var quantityUnitListPresenter: QuantityUnitListPresenter
    private lateinit var quantityUnitListModel: QuantityUnitListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = QuantityUnitListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    override fun showUpdateShopProductScreen(idquantityUnit: String) {
        //loadFragment(AddShopFragment(AddShopFragment.MODE_UPDATE, idShop))
    }

    private fun initialize() {
        binding.layoutQuantityUnitList.visibility = View.INVISIBLE
        quantityUnitListModel = QuantityUnitListModel()
        quantityUnitListPresenter = QuantityUnitListPresenter(this, quantityUnitListModel)
        quantityUnitListModel.getInstance(requireContext())
        quantityUnitListModel.getCurrentLanguage(this)
        quantityUnitListModel.getQuantityUnitList(this)
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterQU.addTextChangedListener { watchText ->
            quantityUnitListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter) {
        binding.rvQU.layoutManager = LinearLayoutManager(this.context)
        binding.rvQU.adapter = quantityUnitAdapter
    }

    private fun initAddquantityUnitClick() {
        binding.addQUItem.setOnClickListener {
            // loadFragment(AddShopFragment(AddShopFragment.MODE_ADD))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslations.put(it.word, it)
        }
        setTranslations()
    }


    private fun setTranslations() {
        binding.layoutQuantityUnitList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["quantityUnits"]!!.text
        // binding.etFilterquantityUnitL.hint = mutableTranslations["search"]!!.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        quantityUnitListModel.getTranslations(this, language.toInt())
    }

    override fun loadData(quantityUnits: List<QuantityUnit>) {
        quantityUnitListPresenter.loadData(quantityUnits)
    }
}