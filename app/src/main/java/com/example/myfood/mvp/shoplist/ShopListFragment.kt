package com.example.myfood.mvp.shoplist

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
import com.example.myfood.databinding.ShopListFragmentBinding
import com.example.myfood.mvp.addshopproduct.AddShopFragment

class ShopListFragment : Fragment(), ShopListContract.View {
    private var _binding: ShopListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListPresenter: ShopListPresenter
    private lateinit var shopListModel: ShopListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = ShopListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    override fun showUpdateShopProductScreen(idShop: String) {
        loadFragment(AddShopFragment(Constant.MODE_UPDATE, idShop))
    }

    private fun initialize() {
        binding.layoutShopList.visibility = View.INVISIBLE
        shopListModel = ShopListModel()
        shopListModel.getInstance(requireContext())
        shopListModel.getUserId(this) { userId -> onUserIdLoaded(userId) }
        shopListModel.getCurrentLanguage(this)
        { currentLanguage -> onCurrentLanguageLoaded(currentLanguage) }
    }

    override fun onUserIdLoaded(idUser: String) {
        shopListPresenter = ShopListPresenter(this, ShopListModel(), idUser)
        initAddShopProductClick()
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterSL.addTextChangedListener { watchText ->
            shopListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(shopListAdapter: ShopListAdapter) {
        binding.rvSL.layoutManager = LinearLayoutManager(this.context)
        binding.rvSL.adapter = shopListAdapter
    }

    private fun initAddShopProductClick() {
        binding.addSLItem.setOnClickListener {
            loadFragment(AddShopFragment(Constant.MODE_ADD))
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


    private fun setTranslations() {
        binding.layoutShopList.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["shoppingList"]!!.text
        binding.etFilterSL.hint = mutableTranslations["search"]!!.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        shopListModel.getTranslations(this, language.toInt())
        { translations -> onTranslationsLoaded(translations) }
    }
}