package com.example.myfood.mvp.pantrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databinding.PurchaseListFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.optionaddpurchase.OptionAddPurchaseFragment

class PantryListFragment : Fragment(), PantryListContract.View {
    private var _binding: PurchaseListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var header: TextView
    private lateinit var purchaseListPresenter: PantryListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PurchaseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun showUpdatePurchaseScreen(idPurchase: String) {
        loadFragment(AddPantryFragment(AddPantryFragment.MODE_UPDATE, idPurchase))
    }

    private fun initialize() {
        binding.header.titleHeader.text = "Purchase List"
        val pantryListModel = PantryListModel()
        pantryListModel.getInstance(requireContext())
        pantryListModel.getUserId(this)
    }

    override fun onUserIdLoaded(idUser: String) {
        purchaseListPresenter = PantryListPresenter(this, PantryListModel(), idUser)
        initAddPurchaseClick()
        initSearcher()
    }


    private fun initSearcher() {
        binding.etFilterPL.addTextChangedListener { watchText ->
            purchaseListPresenter.doFilter(watchText)
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
            loadFragment(OptionAddPurchaseFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
