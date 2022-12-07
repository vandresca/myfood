package com.example.myfood.mvp.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.StorePlace
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.PlaceListFragmentBinding

class PlaceListFragment : Fragment(), PlaceListContract.View {
    private var _binding: PlaceListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeListPresenter: PlaceListPresenter
    private lateinit var placeListModel: PlaceListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PlaceListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    override fun showUpdateShopProductScreen(idPlace: String) {
        //loadFragment(AddShopFragment(AddShopFragment.MODE_UPDATE, idShop))
    }

    private fun initialize() {
        binding.layoutPlaceList.visibility = View.INVISIBLE
        placeListModel = PlaceListModel()
        placeListPresenter = PlaceListPresenter(this, placeListModel)
        placeListModel.getInstance(requireContext())
        placeListModel.getCurrentLanguage(this)
        placeListModel.getPlaceList(this)
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterPlaceL.addTextChangedListener { watchText ->
            placeListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(placeAdapter: PlaceListAdapter) {
        binding.rvPlaceL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPlaceL.adapter = placeAdapter
    }

    private fun initAddPlaceClick() {
        binding.addPlaceLItem.setOnClickListener {
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
        binding.layoutPlaceList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["places"]!!.text
        // binding.etFilterPlaceL.hint = mutableTranslations["search"]!!.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        placeListModel.getTranslations(this, language.toInt())
    }

    override fun loadData(places: List<StorePlace>) {
        placeListPresenter.loadData(places)
    }
}