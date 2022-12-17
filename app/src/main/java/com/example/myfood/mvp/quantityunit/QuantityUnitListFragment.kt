package com.example.myfood.mvp.quantityunit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.MODE_ADD
import com.example.myfood.databasesqlite.entity.QuantityUnit
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.QuantityUnitListFragmentBinding
import com.example.myfood.mvp.addquantityunit.AddQuantityUnitFragment

class QuantityUnitListFragment : Fragment(), QuantityUnitListContract.View {
    private var _binding: QuantityUnitListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var quantityUnitListPresenter: QuantityUnitListPresenter
    private lateinit var quantityUnitListModel: QuantityUnitListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = QuantityUnitListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.layoutQuantityUnitList.visibility = View.INVISIBLE
        quantityUnitListModel = QuantityUnitListModel()
        quantityUnitListPresenter = QuantityUnitListPresenter(
            this,
            quantityUnitListModel, requireContext()
        )
        val currentLanguage = quantityUnitListPresenter.getCurrentLanguage()
        this.mutableTranslations =
            quantityUnitListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        quantityUnitListPresenter.loadData()
        initAddUpdateQuantityUnitClick()
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterQU.addTextChangedListener { watchText ->
            quantityUnitListPresenter.doFilter(watchText)
        }
    }

    override fun showUpdateQuantityUnitScreen(quantityUnitToUpdate: QuantityUnit) {
        loadFragment(
            AddQuantityUnitFragment(
                Constant.MODE_UPDATE,
                quantityUnitToUpdate
            )
        )
    }

    override fun initRecyclerView(quantityUnitAdapter: QuantityUnitListAdapter) {
        binding.rvQU.layoutManager = LinearLayoutManager(this.context)
        binding.rvQU.adapter = quantityUnitAdapter
    }

    private fun initAddUpdateQuantityUnitClick() {
        binding.addQUItem.setOnClickListener {
            loadFragment(AddQuantityUnitFragment(MODE_ADD))
        }
    }

    override fun setTranslations() {
        binding.layoutQuantityUnitList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(Constant.TITLE_QUANTITY_UNIT_LIST)!!.text

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}