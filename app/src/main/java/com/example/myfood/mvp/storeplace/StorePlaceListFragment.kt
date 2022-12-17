package com.example.myfood.mvp.storeplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.StorePlaceListFragmentBinding
import com.example.myfood.mvp.addplace.AddStorePlaceFragment

class StorePlaceListFragment : Fragment(), StorePlaceListContract.View {
    private var _binding: StorePlaceListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeListPresenter: StorePlaceListPresenter
    private lateinit var placeListModel: StorePlaceListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = StorePlaceListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.layoutPlaceList.visibility = View.INVISIBLE
        placeListModel = StorePlaceListModel()
        placeListPresenter = StorePlaceListPresenter(this, placeListModel, requireContext())
        val currentLanguage = placeListPresenter.getCurrentLanguage()
        this.mutableTranslations = placeListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        placeListPresenter.loadData()
        initAddUpdateStorePlaceClick()
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterPlaceL.addTextChangedListener { watchText ->
            placeListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(storePlaceAdapter: StorePlaceListAdapter) {
        binding.rvPlaceL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPlaceL.adapter = storePlaceAdapter
    }

    private fun initAddUpdateStorePlaceClick() {
        binding.addPlaceLItem.setOnClickListener {
            loadFragment(AddStorePlaceFragment(Constant.MODE_ADD))
        }
    }

    override fun showUpdateStorePlaceScreen(storePlaceToUpdate: StorePlace) {
        loadFragment(AddStorePlaceFragment(Constant.MODE_UPDATE, storePlaceToUpdate))
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun setTranslations() {
        binding.layoutPlaceList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(Constant.TITLE_STORE_PLACES_LIST)!!.text
        binding.etFilterPlaceL.hint = mutableTranslations?.get(Constant.FIELD_SEARCH)!!.text
    }
}