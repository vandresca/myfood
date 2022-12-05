package com.example.myfood.mvp.shoplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databinding.ShopListFragmentBinding
import com.example.myfood.mvp.addshopproduct.AddShopFragment

class ShopListFragment : Fragment(), ShopListContract.View {
    private var _binding: ShopListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var header: TextView
    private lateinit var shopListPresenter: ShopListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ShopListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    override fun showUpdateShopProductScreen(idShop: String) {
        loadFragment(AddShopFragment(AddShopFragment.MODE_UPDATE, idShop))
    }

    private fun initialize() {
        binding.header.titleHeader.text = "Shop List"
        val shopListModel = ShopListModel()
        shopListModel.getInstance(requireContext())
        shopListModel.getUserId(this)
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

    override fun initRecyclerView(shopAdapter: ShopListAdapter) {
        binding.rvSL.layoutManager = LinearLayoutManager(this.context)
        binding.rvSL.adapter = shopAdapter
    }

    private fun initAddShopProductClick() {
        binding.addSLItem.setOnClickListener {
            loadFragment(AddShopFragment(AddShopFragment.MODE_ADD))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}